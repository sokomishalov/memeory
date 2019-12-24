import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:memeory/pages/about/about.dart';
import 'package:memeory/pages/appearance/appearance.dart';
import 'package:memeory/pages/channels/channels.dart';
import 'package:memeory/pages/memes/memes_screen_args.dart';
import 'package:memeory/pages/preferences/preferences_wrapper.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:memeory/util/routes/routes.dart';
import 'package:memeory/util/strings/strings.dart';
import 'package:page_transition/page_transition.dart';

import 'memes.dart';

class MemesAppBar extends StatelessWidget {
  const MemesAppBar({Key key, this.screenArgs}) : super(key: key);

  final MemesScreenArgs screenArgs;

  @override
  Widget build(BuildContext context) {
    var caption;

    if (screenArgs.providerId.isNotEmpty) {
      caption = "Provider: ${screenArgs.providerId.capitalize()}";
    } else if (screenArgs.topicId.isNotEmpty) {
      caption = "Topic: ${screenArgs.topicId}";
    } else if (screenArgs.channelId.isNotEmpty) {
      caption = "Channel: ${screenArgs.channelId}";
    } else if (screenArgs.memeId.isNotEmpty) {
      caption = "Meme: ${screenArgs.memeId}";
    } else {
      caption = "All memes";
    }

    return AppBar(
      title: Text(caption),
      actions: <Widget>[
        PopupMenuButton<SettingsItem>(
          icon: Icon(FontAwesomeIcons.userCircle),
          itemBuilder: (BuildContext context) {
            return <SettingsItem>[
              SettingsItem(
                icon: FontAwesomeIcons.rss,
                title: t(context, "channels"),
                pageBuilder: (_) => ChannelPreferences(),
              ),
              SettingsItem(
                icon: FontAwesomeIcons.slidersH,
                title: t(context, "appearance"),
                pageBuilder: (_) => AppearancePreferences(),
              ),
              SettingsItem(
                icon: FontAwesomeIcons.info,
                title: t(context, "about_app"),
                pageBuilder: (_) => AboutApp(),
              ),
            ].map((SettingsItem item) {
              return PopupMenuItem<SettingsItem>(
                value: item,
                child: Row(
                  children: <Widget>[
                    Container(
                      child: Icon(item.icon),
                    ),
                    Container(
                      padding: EdgeInsets.only(left: 5),
                      child: Text(item.title),
                    ),
                  ],
                ),
              );
            }).toList();
          },
          onSelected: (SettingsItem item) {
            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (pageContext) => Scaffold(
                  body: PreferencesPageWrapper(
                    title: item.title,
                    apply: () async {
                      Navigator.pushReplacementNamed(
                        context,
                        ROUTES.MEMES.route,
                        arguments: MemesScreenArgs(),
                        result: PageTransition(
                          type: PageTransitionType.leftToRightWithFade,
                          child: MemesPage(),
                        ),
                      );
                    },
                    applyText: t(context, "back_to_watch_memes"),
                    child: item.pageBuilder(context),
                  ),
                ),
              ),
            );
          },
        ),
      ],
    );
  }
}

class SettingsItem {
  SettingsItem({this.title, this.icon, this.pageBuilder});

  final String title;
  final IconData icon;
  final WidgetBuilder pageBuilder;
}
