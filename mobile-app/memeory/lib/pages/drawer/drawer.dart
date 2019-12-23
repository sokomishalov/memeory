import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:memeory/api/providers.dart';
import 'package:memeory/api/topics.dart';
import 'package:memeory/pages/about/about.dart';
import 'package:memeory/pages/drawer/drawer_header.dart';
import 'package:memeory/pages/drawer/settings_list_tile.dart';
import 'package:memeory/pages/preferences/widgets/channels.dart';
import 'package:memeory/pages/preferences/widgets/orientations.dart';
import 'package:memeory/pages/preferences/widgets/themes.dart';
import 'package:memeory/util/i18n/i18n.dart';

class CustomDrawer extends StatefulWidget {
  @override
  _CustomDrawerState createState() => _CustomDrawerState();
}

class _CustomDrawerState extends State<CustomDrawer> {
  List<dynamic> _topics;
  List<dynamic> _providers;

  @override
  void initState() {
    _topics = [];
    _providers = [];

    super.initState();

    Future.wait([
      fetchTopics(),
      fetchProviders(),
    ]).then((List<List<dynamic>> data) {
      setState(() {
        _topics = data[0];
        _providers = data[1];
      });
    });
  }

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
                Container(
                  padding: const EdgeInsets.only(left: 10),
                  child: Text(
                    "Topics",
                    style: TextStyle(fontSize: 18),
                  ),
                ),
                ...(_topics.map((it) => ListTile(
                      title: Text(it["caption"]),
                    ))),
                Divider(),
                Container(
                  padding: const EdgeInsets.only(left: 10),
                  child: Text(
                    "Providers",
                    style: TextStyle(fontSize: 18),
                  ),
                ),
                ...(_providers.map((it) => ListTile(
                      title: Text(it),
                    ))),
                Divider(),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
