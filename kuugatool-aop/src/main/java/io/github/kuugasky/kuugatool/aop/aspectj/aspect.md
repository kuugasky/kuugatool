## JoinPoint 对象

> JoinPoint对象封装了SpringAop中切面方法的信息,在切面方法中添加JoinPoint参数,就可以获取到封装了该方法信息的JoinPoint对象.

#### 常用api:

- Signature getSignature(); 获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息
- Object[] getArgs(); 获取传入目标方法的参数对象
- Object getTarget(); 获取被代理的对象
- Object getThis(); 获取代理对象

## ProceedingJoinPoint对象

> ProceedingJoinPoint对象是JoinPoint的子接口,该对象只用在@Around的切面方法中,

__添加了以下两个方法__

- Object proceed() throws Throwable //执行目标方法
- Object proceed(Object[] var1) throws Throwable //传入的新的参数去执行目标方法

