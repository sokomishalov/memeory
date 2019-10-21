import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:memeory/util/consts/consts.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:url_launcher/url_launcher.dart';

class AboutApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        children: <Widget>[
          Text(APP_NAME),
          Container(
            padding: EdgeInsets.symmetric(horizontal: 60, vertical: 20),
            child: Text(t(context, "please_contribute")),
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
            child: Text(t(context, "please_donate")),
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
