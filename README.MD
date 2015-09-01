# Content-Extractor

Java library.

Return selector for main content on HTML page.


### Note for use put `jsoup` in classpath.

### Example:

first of all enable bintray repo:

```xml
<!-- enable truerss repo -->
<repositories>
    <repository>
        <id>bintray</id>
        <url>http://dl.bintray.com/truerss/maven</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
</repositories>

<!-- add content extractor and jsoup for dependencies -->
<dependencies>
    <dependency>
        <groupId>org.jsoup</groupId>
        <artifactId>jsoup</artifactId>
        <version>1.8.3</version>
    </dependency>

    <dependency>
        <groupId>com.github.truerss</groupId>
        <artifactId>content-extractor</artifactId>
        <version>0.0.1</version>
    </dependency>
</dependencies>
```

then


```java
import com.github.truerss.ContentExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;

String url = "http://example.com/post.html";
Document doc = Jsoup.connect(url).get();
Element body = doc.select("body").get(0);
ExtractResult result = ContentExtractor.extract(body);
System.out.println("==========> " + result.selector);

```

### License: MIT




