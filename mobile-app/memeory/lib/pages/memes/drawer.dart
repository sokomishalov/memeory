import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:memeory/cache/repository/orientations_repo.dart';
import 'package:memeory/pages/about/about.dart';
import 'package:memeory/pages/preferences/widgets/channels.dart';
import 'package:memeory/pages/preferences/widgets/orientations.dart';
import 'package:memeory/pages/preferences/widgets/themes.dart';
import 'package:memeory/pages/preferences/widgets/wrapper.dart';
import 'package:memeory/util/consts/consts.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:memeory/util/theme/theme.dart';
import 'package:page_transition/page_transition.dart';

import 'memes.dart';

class CustomDrawer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Container(
            height: 110,
            child: DrawerHeader(
              child: Stack(
                children: <Widget>[
                  GestureDetector(
                    onTap: () => Navigator.pop(context),
                    child: Icon(Icons.close),
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[
                      Container(
                        width: 30,
                        height: 30,
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
                        margin: EdgeInsets.only(left: 10),
                        child: Text(
                          'Memeory!',
                          style: TextStyle(
                            fontSize: 15,
                          ),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: ListView(
              itemExtent: 50,
              padding: EdgeInsets.zero,
              children: <Widget>[
                ListTile(
                  leading: Icon(FontAwesomeIcons.rss),
                  title: Text(t(context, "channels")),
                  onTap: () {
                    pushToPrefs(
                      title: t(context, "choose_channels"),
                      context: context,
                      body: ChannelPreferences(),
                    );
                  },
                ),
                ListTile(
                  leading: Icon(FontAwesomeIcons.arrowsAlt),
                  title: Text(t(context, "orientation")),
                  onTap: () {
                    pushToPrefs(
                      title: t(context, "choose_orientation"),
                      context: context,
                      body: OrientationPreferences(),
                    );
                  },
                ),
                ListTile(
                  leading: Icon(FontAwesomeIcons.info),
                  title: Text(t(context, "about_app")),
                  onTap: () {
                    pushToPrefs(
                      title: t(context, "about_app"),
                      context: context,
                      body: AboutApp(),
                    );
                  },
                ),
                ListTile(
                  leading: Icon(FontAwesomeIcons.slidersH),
                  title: Text(t(context, "appearance")),
                  onTap: () {
                    pushToPrefs(
                      title: t(context, "appearance"),
                      context: context,
                      body: ThemePreferences(),
                    );
                  },
                ),
                Divider(),
              ],
            ),
          ),
        ],
      ),
    );
  }

  void pushToPrefs({
    @required BuildContext context,
    String title = "",
    Widget body,
    Future apply,
  }) {
    Navigator.pop(context);
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (pageContext) => Scaffold(
          body: PreferencesPageWrapper(
            title: title,
            apply: () async {
              if (apply != null) await apply;
              var orientation = await getPreferredOrientation();

              Navigator.of(pageContext).pushReplacement(
                PageTransition(
                  type: PageTransitionType.leftToRightWithFade,
                  child: MemesPage(orientation: orientation),
                ),
              );
            },
            applyText: t(context, "back_to_watch_memes"),
            child: body,
          ),
        ),
      ),
    );
  }
}
