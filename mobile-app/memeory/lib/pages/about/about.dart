import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:memeory/util/consts/consts.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:url_launcher/url_launcher.dart';

class AboutApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final buttonWidth = MediaQuery.of(context).size.width - 100;

    return Column(
      children: <Widget>[
        Text("Memeory !"),
        Container(
          margin: EdgeInsets.only(left: 30, right: 30, top: 30, bottom: 5),
          child: Text(t(context, "please_contribute")),
        ),
        Container(
          width: buttonWidth,
          child: RaisedButton.icon(
            icon: Icon(FontAwesomeIcons.github),
            label: Text(GITHUB_REPO),
            color: Colors.black54,
            onPressed: () async {
              await launch(GITHUB_REPO_PAGE);
            },
          ),
        ),
        Container(
          margin: EdgeInsets.only(left: 30, right: 30, top: 30, bottom: 5),
          child: Text("About creator"),
        ),
        Container(
          width: buttonWidth,
          child: RaisedButton.icon(
            icon: Icon(FontAwesomeIcons.info),
            label: Text(USERNAME),
            color: Colors.black45,
            onPressed: () async {
              await launch(ABOUT_ME_PAGE);
            },
          ),
        ),
        Container(
          margin: EdgeInsets.only(left: 30, right: 30, top: 30, bottom: 5),
          child: Text(t(context, "please_donate")),
        ),
        Container(
          width: buttonWidth,
          child: RaisedButton.icon(
            icon: Icon(FontAwesomeIcons.yandexInternational),
            label: Text(USERNAME),
            color: Colors.red,
            onPressed: () async {
              await launch(YANDEX_DONATE_PAGE);
            },
          ),
        ),
      ],
    );
  }
}
