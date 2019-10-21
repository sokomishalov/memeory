import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:memeory/util/consts/consts.dart';
import 'package:url_launcher/url_launcher.dart';

class AboutApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        children: <Widget>[
          Text("Memeory"),
          Container(
            padding: EdgeInsets.symmetric(horizontal: 60, vertical: 20),
            child: Text(
              "If you wanna improve project and contribute, visit github repo",
            ),
          ),
          RaisedButton.icon(
            icon: Icon(FontAwesomeIcons.github),
            label: Text(GITHUB_REPO),
            color: Colors.black54,
            onPressed: () async {
              await launch(GITHUB_REPO_PAGE);
            },
          ),
          Container(
            padding: EdgeInsets.symmetric(horizontal: 60, vertical: 20),
            child: Text(
              "If you wanna help with money",
            ),
          ),
          RaisedButton.icon(
            icon: Icon(FontAwesomeIcons.yandexInternational),
            label: Text(USERNAME),
            color: Colors.red,
            onPressed: () async {
              await launch(YANDEX_DONATE_PAGE);
            },
          ),
        ],
      ),
    );
  }
}
