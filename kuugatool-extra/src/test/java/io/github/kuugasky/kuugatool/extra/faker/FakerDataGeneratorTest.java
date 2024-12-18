package io.github.kuugasky.kuugatool.extra.faker;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static io.github.kuugasky.kuugatool.extra.faker.FakerType.ENGLISH;

class FakerDataGeneratorTest {

    // public static void main(String[] args) {
    //     System.out.println(new Faker().internet().url());
    // }

    @Test
    void generateFakerListData() {
        FakerDataGenerator build = FakerDataGenerator.build().listLoopCount(1)
                .englishFields(List.of("remark"))
                .englishFields(Map.of("more", 1, "content", 2))
                .urlFields(List.of("pictureUrl"));
        System.out.println(StringUtil.formatString(build.generateFakeListData(User.class)));
    }

    @Test
    void generateFakerData() {
        User user = new User();
        FakerDataGenerator build = FakerDataGenerator.build();
        System.out.println(StringUtil.formatString(build.generateFakeData(user)));
    }

    @Test
    void generateFakerDataWord() {
        XkwWordInfo wordInfo = new XkwWordInfo();
        FakerDataGenerator build = FakerDataGenerator.build();
        System.out.println(StringUtil.formatString(build.generateFakeData(wordInfo)));
    }

    @Test
    void generateFakerDataUser2() {
        User2 user = new User2();
        FakerDataGenerator build = FakerDataGenerator.build();
        System.out.println(StringUtil.formatString(build.generateFakeData(user)));
    }

    @Test
    void generateFakerDataEnglish() {
        User user = new User();
        FakerDataGenerator.buildEnglish().generateFakeData(user);
        System.out.println(StringUtil.formatString(user));
    }

    @Test
    void generateFakeDataByFakerType() {
        User user = new User();
        FakerDataGenerator.build(ENGLISH).generateFakeData(user);
        System.out.println(StringUtil.formatString(user));
    }

}

@Data
class User2 {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Integer aage;

}

class XkwWordInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer eId;

    /**
     * 书本id
     */
    private Integer bookId;

    /**
     * 章ID
     */
    private String catalogId;

    /**
     * 单词ID
     */
    private String wordId;

    /**
     * 单词名称
     */
    private String wordName;

    /**
     * 拼写设置新的单词名称
     */
    private String newWordName;

    /**
     * 单词释义
     */
    private String wordShiyi;

    /**
     * 单词音频
     */
    private String wordMp3;

    /**
     * 单词音标
     */
    private String wordYinbiao;

    /**
     * 单词音标 2024-04-23 大模型导入
     */
    private String yinbiaoNew;

    /**
     * 单词例句
     */
    private String wordLiju;

    private String wordLijuNew;

    /**
     * 单词图片
     */
    private String wordPicture;

    /**
     * (拼写试题)状态 1:启用 0:不启用
     */
    private Integer state;

    /**
     * 单词状态 0: 不启用 1:启用
     */
    private Integer wordState;

    /**
     * 出版社id
     */
    private Integer versionId;

    private Integer wordSource;

    private Integer handleType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer geteId() {
        return eId;
    }

    public void seteId(Integer eId) {
        this.eId = eId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getNewWordName() {
        return newWordName;
    }

    public void setNewWordName(String newWordName) {
        this.newWordName = newWordName;
    }

    public String getWordShiyi() {
        return wordShiyi;
    }

    public void setWordShiyi(String wordShiyi) {
        this.wordShiyi = wordShiyi;
    }

    public String getWordMp3() {
        return wordMp3;
    }

    public void setWordMp3(String wordMp3) {
        this.wordMp3 = wordMp3;
    }

    public String getWordYinbiao() {
        return wordYinbiao;
    }

    public void setWordYinbiao(String wordYinbiao) {
        this.wordYinbiao = wordYinbiao;
    }

    public String getYinbiaoNew() {
        return yinbiaoNew;
    }

    public void setYinbiaoNew(String yinbiaoNew) {
        this.yinbiaoNew = yinbiaoNew;
    }

    public String getWordLiju() {
        return wordLiju;
    }

    public void setWordLiju(String wordLiju) {
        this.wordLiju = wordLiju;
    }

    public String getWordLijuNew() {
        return wordLijuNew;
    }

    public void setWordLijuNew(String wordLijuNew) {
        this.wordLijuNew = wordLijuNew;
    }

    public String getWordPicture() {
        return wordPicture;
    }

    public void setWordPicture(String wordPicture) {
        this.wordPicture = wordPicture;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getWordState() {
        return wordState;
    }

    public void setWordState(Integer wordState) {
        this.wordState = wordState;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getWordSource() {
        return wordSource;
    }

    public void setWordSource(Integer wordSource) {
        this.wordSource = wordSource;
    }

    public Integer getHandleType() {
        return handleType;
    }

    public void setHandleType(Integer handleType) {
        this.handleType = handleType;
    }

    @Override
    public String toString() {
        return "XkwWordInfo{" +
                "id=" + id +
                ", eId=" + eId +
                ", bookId=" + bookId +
                ", catalogId=" + catalogId +
                ", wordId=" + wordId +
                ", wordName=" + wordName +
                ", newWordName=" + newWordName +
                ", wordShiyi=" + wordShiyi +
                ", wordMp3=" + wordMp3 +
                ", wordYinbiao=" + wordYinbiao +
                ", yinbiaoNew=" + yinbiaoNew +
                ", wordLiju=" + wordLiju +
                ", wordLijuNew=" + wordLijuNew +
                ", wordPicture=" + wordPicture +
                ", state=" + state +
                ", wordState=" + wordState +
                ", versionId=" + versionId +
                ", wordSource=" + wordSource +
                ", handleType=" + handleType +
                "}";
    }
}
