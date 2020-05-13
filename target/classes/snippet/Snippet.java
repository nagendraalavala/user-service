package snippet;

public class Snippet {
	<server description="Baeldung Open Liberty server">
	    <featureManager>
	        <feature>mpHealth-2.0</feature>
	    </featureManager>
	    <webApplication location="open-liberty.war" contextRoot="/" />
	    <httpEndpoint host="*" httpPort="${default.http.port}"
	      httpsPort="${default.https.port}" id="defaultHttpEndpoint" />
	</server>
}

