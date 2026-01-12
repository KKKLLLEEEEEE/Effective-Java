package chapter2.item2;

/**
 * 演示 Effective Java 第2条：遇到多个构造器参数时要考虑使用构建器
 * 
 * 真实场景：HTTP 请求构建器
 * - 展示 Builder 在实际开发中的应用
 * - 多个可选参数（headers, timeout, retry等）
 * 
 * @author CN-JeffreyZhou
 */
public class Item2_Builder_RealWorld {
    
    static class HttpRequest {
        private final String url;              // 必需
        private final String method;           // 必需
        private final String body;             // 可选
        private final int timeout;             // 可选
        private final int retryCount;          // 可选
        private final boolean followRedirects; // 可选
        
        public static class Builder {
            // 必需参数
            private final String url;
            private final String method;
            
            // 可选参数
            private String body = "";
            private int timeout = 30000;
            private int retryCount = 0;
            private boolean followRedirects = true;
            
            public Builder(String url, String method) {
                this.url = url;
                this.method = method;
            }
            
            public Builder body(String val) {
                body = val;
                return this;
            }
            
            public Builder timeout(int val) {
                timeout = val;
                return this;
            }
            
            public Builder retryCount(int val) {
                retryCount = val;
                return this;
            }
            
            public Builder followRedirects(boolean val) {
                followRedirects = val;
                return this;
            }
            
            public HttpRequest build() {
                return new HttpRequest(this);
            }
        }
        
        private HttpRequest(Builder builder) {
            url = builder.url;
            method = builder.method;
            body = builder.body;
            timeout = builder.timeout;
            retryCount = builder.retryCount;
            followRedirects = builder.followRedirects;
        }
        
        @Override
        public String toString() {
            return String.format("HttpRequest[%s %s, timeout=%dms, retry=%d, redirect=%s]",
                    method, url, timeout, retryCount, followRedirects);
        }
    }
    
    public static void main(String[] args) {
        // 场景1：简单的 GET 请求
        HttpRequest getRequest = new HttpRequest.Builder("https://api.example.com/users", "GET")
                .build();
        System.out.println("GET请求：" + getRequest);
        
        // 场景2：带超时和重试的 POST 请求
        HttpRequest postRequest = new HttpRequest.Builder("https://api.example.com/orders", "POST")
                .body("{\"product\":\"book\",\"quantity\":2}")
                .timeout(5000)
                .retryCount(3)
                .build();
        System.out.println("POST请求：" + postRequest);
        
        // 场景3：不跟随重定向的请求
        HttpRequest noRedirect = new HttpRequest.Builder("https://short.url/abc", "GET")
                .followRedirects(false)
                .timeout(10000)
                .build();
        System.out.println("不跟随重定向：" + noRedirect);
    }
}
