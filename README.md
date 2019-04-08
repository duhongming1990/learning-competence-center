# learning-competence-center

#### 项目介绍
能力中心

1.Ioc容器
BeanFactory（基本接口）
DefaultListableBeanFactory（初步实现类）
XmlBeanFactory（读取XML文件-实现类）

ApplicationContext（高级接口）
AbstractXmlApplicationContext（初步实现类）
FileSystemXmlApplicationContext(读取XML文件-实现类)

2.AOP实现

aopalliance:
Advice

springframework:
BeforeAdvice--->MethodBeforeAdvice
AfterAdvice--->AfterReturningAdvice
ThrowsAdvice

Pointcut：
StaticMethodMatcherPointcut
DynamicMethodMatcherPointcut
ExpressionPointcut
AnnotationMatchingPointcut

AopProxy：
JdkDynamicAopProxy
CglibAopProxy
