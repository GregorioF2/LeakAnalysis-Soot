**Requirements**
- *maven:*
  - download: http://maven.apache.org/download.cgi
  - install: http://maven.apache.org/install.html

- *Java 8:*
  - install https://github.com/shyiko/jabba
  - run jabba install zulu@1.8.92

```(With higher version of java, the following error appear `java.lang.RuntimeException: Error: cannot find rt.jar.`. Error kindof explained in here https://github.com/eclipse-cognicrypt/CogniCrypt/issues/202#issuecomment-431386825)```

**Run the analysis**

Leak sensible vairables analysis.

To test the analsysis with the example implemented on `src/main/java/examples`, just run from `srs` folder the command

```
mvn clean install compile

java -cp target/leak-analysis-1.0-SNAPSHOT-jar-with-dependencies.jar  Launcher -keep-line-number -f J -w -pp -cp target/classes/ examples.Main
```


**To install Datalog** (Docs:  https://www.ccs.neu.edu/home/ramsdell/tools/datalog/datalog.html)
- Download: https://wwu-pi.github.io/tutorials/lectures/lsp/020_install_des.html
- Use zulu@1.8.92
- sudo apt install libcanberra-gtk-module libcanberra-gtk3-module
