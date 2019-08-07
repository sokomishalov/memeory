import 'package:flutter/material.dart';
import 'package:memeory/components/app_bar/app_bar.dart';
import 'package:memeory/pages/about/about.dart';
import 'package:memeory/pages/preferences/widgets/channels.dart';
import 'package:memeory/pages/preferences/widgets/orientations.dart';
import 'package:memeory/pages/preferences/widgets/socials.dart';
import 'package:memeory/pages/preferences/widgets/wrapper.dart';
import 'package:memeory/strings/ru.dart';
import 'package:memeory/theme/theme.dart';
import 'package:memeory/util/consts.dart';

class ProfilePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: buildAppBar(PROFILE),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          DrawerHeader(
            child: Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Container(
                    width: 70,
                    height: 70,
                    decoration: BoxDecoration(
                      shape: BoxShape.circle,
                      image: DecorationImage(
                        image: dependingOnThemeChoice(
                          context: context,
                          light: AssetImage(LOGO_ASSET),
                          dark: AssetImage(LOGO_INVERTED_ASSET),
                        ),
                      ),
                    ),
                  ),
                  Container(
                    padding: EdgeInsets.only(top: 10),
                    child: Text('$APP_NAME!'),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: ListView(
              padding: EdgeInsets.zero,
              children: <Widget>[
                ListTile(
                  leading: Icon(Icons.person_outline),
                  title: Text(SOCIAL_NETWRORKS),
                  onTap: () {
                    pushToPrefs(
                      header: SOCIAL_NETWRORKS,
                      title: PLEASE_AUTHORIZE,
                      context: context,
                      body: SocialPreferences(),
                    );
                  },
                ),
                ListTile(
                  leading: Icon(Icons.star_border),
                  title: Text(CHANNELS),
                  onTap: () {
                    pushToPrefs(
                      header: CHANNELS,
                      title: CHOOSE_CHANNELS,
                      context: context,
                      body: ChannelPreferences(),
                    );
                  },
                ),
                ListTile(
                  leading: Icon(Icons.rss_feed),
                  title: Text(ORIENTATION),
                  onTap: () {
                    pushToPrefs(
                      header: ORIENTATION,
                      title: CHOOSE_ORIENTATION,
                      context: context,
                      body: OrientationPreferences(),
                    );
                  },
                ),
                ListTile(
                  leading: Icon(Icons.info_outline),
                  title: Text(ABOUT_APP),
                  onTap: () {
                    pushToPrefs(
                      header: ABOUT_APP,
                      title: ABOUT_APP,
                      context: context,
                      body: AboutApp(),
                    );
                  },
                )
              ],
            ),
          ),
          Container(
            height: 50,
            margin: EdgeInsets.only(bottom: 20),
            child: SwitchListTile(
              title: Text("Темная тема"),
              value: Theme.of(context).brightness == Brightness.dark,
              onChanged: (value) async {
                await changeTheme(context);
              },
            ),
          ),
        ],
      ),
    );
  }

  void pushToPrefs({
    BuildContext context,
    String header,
    String title,
    Widget body,
    Future apply,
  }) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (pageContext) => Scaffold(
          appBar: buildAppBar(header ?? title ?? EMPTY),
          body: PreferencesPageWrapper(
            title: title ?? EMPTY,
            apply: () async {
              if (apply != null) await apply;
              Navigator.of(pageContext).pop();
            },
            applyText: BACK,
            child: body,
          ),
        ),
      ),
    );
  }
}
