import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';

class MemeoryWebView extends StatelessWidget {
  const MemeoryWebView({Key key, this.url}) : super(key: key);

  final String url;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: WebView(
        initialUrl: url,
        javascriptMode: JavascriptMode.unrestricted,
      ),
    );
  }
}

openMemeoryWebView(BuildContext context, String url) {
  Navigator.of(context).push(
    MaterialPageRoute(
      builder: (_) => MemeoryWebView(
        url: url,
      ),
    ),
  );
}
