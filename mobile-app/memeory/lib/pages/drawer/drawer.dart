import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:memeory/pages/about/about.dart';
import 'package:memeory/pages/drawer/drawer_header.dart';
import 'package:memeory/pages/drawer/settings_list_tile.dart';
import 'package:memeory/pages/preferences/widgets/channels.dart';
import 'package:memeory/pages/preferences/widgets/orientations.dart';
import 'package:memeory/pages/preferences/widgets/themes.dart';
import 'package:memeory/util/i18n/i18n.dart';

class CustomDrawer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          MemeoryDrawerHeader(),
          Expanded(
            child: ListView(
              itemExtent: 50,
              padding: EdgeInsets.zero,
              children: <Widget>[
                SettingsListTile(
                  icon: FontAwesomeIcons.rss,
                  title: t(context, "channels"),
                  pageBuilder: (_) => ChannelPreferences(),
                ),
                SettingsListTile(
                  icon: FontAwesomeIcons.arrowsAlt,
                  title: t(context, "orientation"),
                  pageBuilder: (_) => OrientationPreferences(),
                ),
                SettingsListTile(
                  icon: FontAwesomeIcons.info,
                  title: t(context, "about_app"),
                  pageBuilder: (_) => AboutApp(),
                ),
                SettingsListTile(
                  icon: FontAwesomeIcons.slidersH,
                  title: t(context, "appearance"),
                  pageBuilder: (_) => ThemePreferences(),
                ),
                Divider(),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
