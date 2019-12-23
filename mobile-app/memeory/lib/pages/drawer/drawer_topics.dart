import 'package:flutter/material.dart';
import 'package:memeory/api/topics.dart';

class DrawerTopics extends StatefulWidget {
  @override
  _DrawerTopicsState createState() => _DrawerTopicsState();
}

class _DrawerTopicsState extends State<DrawerTopics> {
  List<dynamic> _topics;

  @override
  void initState() {
    _topics = [];

    super.initState();

    fetchTopics().then((List<dynamic> topics) {
      setState(() {
        _topics = topics;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: _topics.map((it) {
        return ListTile(
          title: Text(it["caption"]),
        );
      }).toList(),
    );
  }
}
