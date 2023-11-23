package io.github.kuugasky.kuugatool.core.img.gif;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Adapted from Jef Poskanzer's Java port by way of J. M. G. Elliott.
 * <p>
 * K Weiner 12/00
 */
class LZWEncoder {

    private static final int EOF = -1;

    private final int imgW;
    private final int imgH;
    private final byte[] pixAry;
    private final int initCodeSize;

    private int remaining;
    private int curPixel;

    // GIFCOMPR.C       - GIF Image compression routines
    //
    // Lempel-Ziv compression based on 'compress'.  GIF modifications by
    // David Rowley (mgardi@watdcsu.waterloo.edu)

    // General DEFINEs

    static final int BITS = 12;
    /**
     * 80% occupancy
     */
    static final int HSIZE = 5003;

    // GIF Image compression - modified 'compress'
    //
    // Based on: compress.c - File compression ala IEEE Computer, June 1984.
    //
    // By Authors:  Spencer W. Thomas      (decvax!harpo!utah-cs!utah-gr!thomas)
    //              Jim McKie              (decvax!mcvax!jim)
    //              Steve Davies           (decvax!vax135!petsd!peora!srd)
    //              Ken Turkowski          (decvax!decwrl!turtlevax!ken)
    //              James A. Woods         (decvax!ihnp4!ames!jaw)
    //              Joe Orost              (decvax!vax135!petsd!joe)

    /**
     * number of bits/code
     */
    int nBits;
    /**
     * user settable max # bits/code
     */
    int maxbits = BITS;
    /**
     * maximum code, given n_bits
     */
    int maxcode;
    /**
     * should NEVER generate this code
     */
    int maxmaxcode = 1 << BITS;

    int[] htab = new int[HSIZE];
    int[] codetab = new int[HSIZE];
    /**
     * for dynamic table sizing
     */
    int hsize = HSIZE;
    /**
     * first unused entry
     */
    int freeEnt = 0;
    /**
     * block compression parameters -- after all codes are used up,
     * and compression rate changes, start over.
     */
    boolean clearFlg = false;

    // Algorithm:  use open addressing double hashing (no chaining) on the
    // prefix code / next character combination.  We do a variant of Knuth's
    // algorithm D (vol. 3, sec. 6.4) along with G. Knott's relatively-prime
    // secondary probe.  Here, the modular division first probe is gives way
    // to a faster exclusive-or manipulation.  Also do block compression with
    // an adaptive reset, whereby the code table is cleared when the compression
    // ratio decreases, but after the table fills.  The variable-length output
    // codes are re-sized at this point, and a special CLEAR code is generated
    // for the decompressor.  Late addition:  construct the table according to
    // file size for noticeable speed improvement on small files.  Please direct
    // questions about this implementation to ames!jaw.

    int gInitBits;

    int clearCode;
    int eofcode;

    // output
    //
    // Output the given code.
    // Inputs:
    //      code:   A n_bits-bit integer.  If == -1, then EOF.  This assumes
    //              that n_bits =< wordsize - 1.
    // Outputs:
    //      Outputs code to the file.
    // Assumptions:
    //      Chars are 8 bits long.
    // Algorithm:
    //      Maintain a BITS character long buffer (so that 8 codes will
    // fit in it exactly).  Use the VAX insv instruction to insert each
    // code in turn.  When the buffer fills up empty it and start over.

    int curAccum = 0;
    int curBits = 0;

    final int[] masks =
            {
                    0x0000,
                    0x0001,
                    0x0003,
                    0x0007,
                    0x000F,
                    0x001F,
                    0x003F,
                    0x007F,
                    0x00FF,
                    0x01FF,
                    0x03FF,
                    0x07FF,
                    0x0FFF,
                    0x1FFF,
                    0x3FFF,
                    0x7FFF,
                    0xFFFF};

    // Number of characters so far in this 'packet'
    int aCount;

    // Define the storage for the packet accumulator
    byte[] accum = new byte[256];

    //----------------------------------------------------------------------------
    LZWEncoder(int width, int height, byte[] pixels, int colorDepth) {
        imgW = width;
        imgH = height;
        pixAry = pixels;
        initCodeSize = Math.max(2, colorDepth);
    }

    /**
     * Add a character to the end of the current packet, and if it is 254
     * characters, flush the packet to disk.
     *
     * @param c    c
     * @param outs uts
     * @throws IOException IOException
     */
    void charOut(byte c, OutputStream outs) throws IOException {
        accum[aCount++] = c;
        if (aCount >= 254) {
            flushChar(outs);
        }
    }

    // Clear out the hash table

    /**
     * table clear for block compress
     *
     * @param outs outs
     * @throws IOException IOException
     */
    void clBlock(OutputStream outs) throws IOException {
        clHash(hsize);
        freeEnt = clearCode + 2;
        clearFlg = true;

        output(clearCode, outs);
    }

    /**
     * reset code table
     *
     * @param hsize hsize
     */
    void clHash(int hsize) {
        for (int i = 0; i < hsize; ++i) {
            htab[i] = -1;
        }
    }

    void compress(int initBits, OutputStream outs) throws IOException {
        int fcode;
        int i /* = 0 */;
        int c;
        int ent;
        int disp;
        int hsizeReg;
        int hshift;

        // Set up the globals:  g_init_bits - initial number of bits
        gInitBits = initBits;

        // Set up the necessary values
        clearFlg = false;
        nBits = gInitBits;
        maxcode = maxCode(nBits);

        clearCode = 1 << (initBits - 1);
        eofcode = clearCode + 1;
        freeEnt = clearCode + 2;
        // clear packet
        aCount = 0;

        ent = nextPixel();

        hshift = 0;
        int i1 = 65536;
        for (fcode = hsize; fcode < i1; fcode *= 2) {
            ++hshift;
        }
        // set hash code range bound
        hshift = 8 - hshift;

        hsizeReg = hsize;
        // clear hash table
        clHash(hsizeReg);

        output(clearCode, outs);

        outer_loop:
        while ((c = nextPixel()) != EOF) {
            fcode = (c << maxbits) + ent;
            // xor hashing
            i = (c << hshift) ^ ent;

            if (htab[i] == fcode) {
                ent = codetab[i];
                continue;
            } else if (htab[i] >= 0)
            // non-empty slot
            {
                // secondary hash (after G. Knott)
                disp = hsizeReg - i;
                if (i == 0) {
                    disp = 1;
                }
                do {
                    if ((i -= disp) < 0) {
                        i += hsizeReg;
                    }

                    if (htab[i] == fcode) {
                        ent = codetab[i];
                        continue outer_loop;
                    }
                } while (htab[i] >= 0);
            }
            output(ent, outs);
            ent = c;
            if (freeEnt < maxmaxcode) {
                // code -> hashtable
                codetab[i] = freeEnt++;
                htab[i] = fcode;
            } else {
                clBlock(outs);
            }
        }
        // Put out the final code.
        output(ent, outs);
        output(eofcode, outs);
    }

    //----------------------------------------------------------------------------

    void encode(OutputStream os) throws IOException {
        // write "initial code size" byte
        os.write(initCodeSize);
        // reset navigation variables
        remaining = imgW * imgH;
        curPixel = 0;
        // compress and write the pixel data
        compress(initCodeSize + 1, os);
        // write block terminator
        os.write(0);
    }

    /**
     * Flush the packet to disk, and reset the accumulator
     *
     * @param outs outs
     * @throws IOException IOException
     */
    void flushChar(OutputStream outs) throws IOException {
        if (aCount > 0) {
            outs.write(aCount);
            outs.write(accum, 0, aCount);
            aCount = 0;
        }
    }

    final int maxCode(int nBits) {
        return (1 << nBits) - 1;
    }

    //----------------------------------------------------------------------------
    // Return the next pixel from the image
    //----------------------------------------------------------------------------

    private int nextPixel() {
        if (remaining == 0) {
            return EOF;
        }

        --remaining;

        byte pix = pixAry[curPixel++];

        return pix & 0xff;
    }

    void output(int code, OutputStream outs) throws IOException {
        curAccum &= masks[curBits];

        if (curBits > 0) {
            curAccum |= (code << curBits);
        } else {
            curAccum = code;
        }

        curBits += nBits;

        while (curBits >= 8) {
            charOut((byte) (curAccum & 0xff), outs);
            curAccum >>= 8;
            curBits -= 8;
        }

        // If the next entry is going to be too big for the code size,
        // then increase it, if possible.
        if (freeEnt > maxcode || clearFlg) {
            if (clearFlg) {
                maxcode = maxCode(nBits = gInitBits);
                clearFlg = false;
            } else {
                ++nBits;
                if (nBits == maxbits) {
                    maxcode = maxmaxcode;
                } else {
                    maxcode = maxCode(nBits);
                }
            }
        }

        if (code == eofcode) {
            // At EOF, write the rest of the buffer.
            while (curBits > 0) {
                charOut((byte) (curAccum & 0xff), outs);
                curAccum >>= 8;
                curBits -= 8;
            }

            flushChar(outs);
        }
    }

}
