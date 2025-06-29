# 坑

## 1. 使用 `Collectors.toMap()` 要注意，key不能重复，不确定时，可使用下面这个3个参数的方法
```java
Map<String, Integer> nameToAge = people.stream()
    .collect(Collectors.toMap(
        Person::getName,
        Person::getAge,
        (first, second) -> second  // Keep last value
    ));
```