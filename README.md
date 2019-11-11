# snoti-demo

### 快速上手
1. 打开 `snoti-demo/src/main/resources/application.yml`, 并按需修改 `snoti` 配置信息. (一般来讲只需要配置 `item-list`) 
2. 按需修改对应的 `handle, (如DeviceOnlineHandler)`
3. 在项目根目录执行 `mvn clean package -Dmaven.test.skip=true -U`
4. 通过` java -jar target/snoti-demo.jar ` 运行demo

