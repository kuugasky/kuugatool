# 分组

```
// 按照sn分组:  List<Map<String, Object>> dataList
Map<String, List<Map<String, Object>>>dataMap=dataList.stream().collect(Collectors.groupingBy(e->e.get("sn")+""));
//按照职员部分分组: List<Employee> list
Map<String, List<Employee>>collect=list.stream().collect(Collectors.groupingBy(i->i.getUnitName()));
//多条件分组
Map<String, Map<String, List<Employee>>>collect=list.stream().collect(Collectors.groupingBy(i->i.getUnitName(),Collectors.groupingBy(i->i.getWorkType())));
```

# 过滤

```
//根据指定sn,过滤出符合的数据: List<Map<String, Object>> deviceDataList
List<Map<String, Object>>tempDeviceDataList=deviceDataList.stream().filter(map->map.get("sn").toString().equals(sn)).collect(Collectors.toList());
//筛选出工资大于10000的职员
List<Employee> newList=list.stream().filter(item->{
return item.getSalary().compareTo(new BigDecimal(10000))>0&&!item.getWorkType().equals("项目经理");
}).collect(Collectors.toList());
```

# List和Map互转

## list转map

```
// (k1,k2)->k2 避免键重复 k1-取第一个数据；k2-取最后一条数据
//key和value,都可以根据传入的值返回不同的Map
Map<String, String> deviceMap=hecmEnergyDevicesList.stream().collect(Collectors.toMap(i->i.getDeviceNum(),j->j.getDeviceName(),(k1,k2)->k1));
//
Map<String, Object> map=list.stream().collect(Collectors.toMap(i->i.getEmpName()+i.getUnitName(),j->j,(k1,k2)->k1));
```

## map转list

```
//在.map里面构造数据 return什么数据就转成什么类型的list
List<Employee> collect=map.entrySet().stream().map(item->{
    Employee employee=new Employee();
    employee.setId(item.getKey());
    employee.setEmpName(item.getValue());
    return employee;
}).collect(Collectors.toList());
```

# 求和/极值

```
//在egyList里面求cols的和
public static BigDecimal getSumBig(List<Map<String, Object>>egyList,String cols){
    BigDecimal consuBig=egyList.stream()
            .filter((Map m)->StringUtils.isNotEmpty(m.get(cols)+"")&&!"null".equals(String.valueOf(m.get(cols)))
                &&!"-".equals(String.valueOf(m.get(cols))))
            .map((Map m)->new BigDecimal(m.get(cols)+""))
            .reduce(BigDecimal.ZERO,BigDecimal::add);
        return consuBig;
}
//List<Employee> list
//Bigdecimal求和/极值:
BigDecimal sum=list.stream().map(Employee::getSalary).reduce(BigDecimal.ZERO,BigDecimal::add);
BigDecimal max=list.stream().map(Employee::getSalary).reduce(BigDecimal.ZERO,BigDecimal::max);
//基本数据类型求和/极值:
Integer sum=list.stream().mapToInt(Employee::getId).sum();
OptionalInt optionalMax=list.stream().mapToInt(Employee::getId).max();
optionalMax.getAsInt();
```

# 求最大/最小值的对象

```
Optional<Employee> optional=list.stream().collect(Collectors.maxBy(Comparator.comparing(Employee::getId)));
    if(optional.isPresent()){ // 判断是否有值
        Employee user=optional.get();
    }
return optional.orElse(new Employee());
```

# 去重

```
//去重之后进行拼接: List<String> deviceNodeList
Srting deviceNodeStr=deviceNodeList.stream().distinct().collect(Collectors.joining("','"));
//直接去重返回list
// List<String> deviceIdList
List<String> deviceIdList=deviceIdList.stream().distinct().collect(Collectors.toList());
```

# 排序

```
//按照时间排序 1升 -1降
Collections.sort(listFast,(p1,p2)->{
    return String.valueOf(p1.get("time")).compareTo(p2.get("time")+"");
});
// s1-s2 升序 s2-s1降序
Collections.sort(list,(s1,s2)->s1.getSalary().compareTo(s2.getSalary()));
//多条件排序: List<Employee> list, s1-s2 升序 s2-s1降序
list.sort(Comparator.comparing(Employee::getSalary).reversed().thenComparing(Employee::getId).reversed());
```

# 拼接

```
//统计：和、数量、最大值、最小值、平均值: List<Employee> list
IntSummaryStatistics collect=list.stream().collect(Collectors.summarizingInt(Employee::getId));
System.out.println("和："+collect.getSum());
System.out.println("数量："+collect.getCount());
System.out.println("最大值："+collect.getMax());
System.out.println("最小值："+collect.getMin());
System.out.println("平均值："+collect.getAverage());
```

# 平均值

```
OptionalDouble average=list.stream().mapToInt(Employee::getId).average();
average.getAsDouble();
```

# 某个值的数量

```
//List<Employee> list
Map<BigDecimal, Long> collect=list.stream().collect(Collectors.groupingBy(i->i.getSalary(),Collectors.counting()));
//List<Map<String,Object>> egyList
long count = egyList.stream()
        .filter((Map m)->StringUtils.isNotEmpty(m.get(cols)+""))
        .map((Map m)->new BigDecimal(m.get(cols)+""))
        .count();
```

# 分区

```
//List<Employee> list
//单层分区
Map<Boolean, List<Employee>>collect=list.stream().collect(Collectors.partitioningBy(i->i.getId()==1));
//多层分区
Map<Boolean, Map<Boolean, List<Employee>>>collect=list.stream().collect(Collectors.partitioningBy(i->i.getId()==1,Collectors.partitioningBy(i->i.getSalary().compareTo(new BigDecimal(20000))==0)));
```
