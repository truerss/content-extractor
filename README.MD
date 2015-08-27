# Content-Extractor

Java library.

Return selector for main content on HTML page.


### Note for use put `jsoup` in classpath.

### Example:

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




