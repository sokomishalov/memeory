import 'package:flutter/material.dart';
import 'package:memeory/cache/repository/orientations_repo.dart';
import 'package:memeory/model/orientation.dart';
import 'package:memeory/pages/about/about.dart';
import 'package:memeory/pages/memes/memes_horizontal.dart';
import 'package:memeory/pages/memes/memes_vertical.dart';
import 'package:memeory/pages/preferences/widgets/channels.dart';
import 'package:memeory/pages/preferences/widgets/orientations.dart';
import 'package:memeory/pages/preferences/widgets/socials.dart';
import 'package:memeory/pages/preferences/widgets/wrapper.dart';
import 'package:memeory/strings/ru.dart';
import 'package:memeory/theme/dark.dart';
import 'package:memeory/theme/light.dart';
import 'package:memeory/theme/theme.dart';
import 'package:memeory/util/consts.dart';
import 'package:page_transition/page_transition.dart';

class MemesPage extends StatelessWidget {
  const MemesPage({
    this.orientation = MemesOrientation.VERTICAL,
  });

  final MemesOrientation orientation;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: dependingOnThemeChoice(
        context: context,
        light: MEME_BACKGROUND_COLOR_LIGHT,
        dark: MEME_BACKGROUND_COLOR_DARK,
      ),
      appBar: PreferredSize(
        preferredSize: Size.fromHeight(50),
        child: AppBar(
          backgroundColor: dependingOnThemeChoice(
            context: context,
            light: APP_BAR_COLOR_LIGHT,
            dark: APP_BAR_COLOR_DARK,
          ),
          iconTheme: getDefaultIconThemeData(context),
          centerTitle: true,
          title: Text(
            MEMES,
            style: TextStyle(
              color: dependingOnThemeChoice(
                context: context,
                light: TEXT_COLOR_LIGHT,
                dark: TEXT_COLOR_DARK,
              ),
            ),
          ),
        ),
      ),
      drawer: Drawer(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            DrawerHeader(
              child: Stack(
                children: <Widget>[
                  GestureDetector(
                    onTap: () => Navigator.pop(context),
                    child: Icon(Icons.close),
                  ),
                  Center(
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
                ],
              ),
            ),
            Expanded(
              child: ListView(
                padding: EdgeInsets.zero,
                children: <Widget>[
                  ListTile(
                    leading: Icon(Icons.person_outline),
                    title: Text(PROFILE),
                    onTap: () {
                      pushToPrefs(
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
                  Navigator.pop(context);
                  await changeTheme(context);
                },
              ),
            ),
          ],
        ),
      ),
      body: orientation == MemesOrientation.VERTICAL
          ? MemesVertical()
          : MemesHorizontal(),
    );
  }

  void pushToPrefs({
    BuildContext context,
    String title,
    Widget body,
    Future apply,
  }) {
    Navigator.pop(context);
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (pageContext) => Scaffold(
          body: PreferencesPageWrapper(
            title: title ?? EMPTY,
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
            applyText: BACK_TO_WATCH_MEMES,
            child: body,
          ),
        ),
      ),
    );
  }
}
