import 'package:flutter/material.dart';
import 'package:memeory/model/orientation.dart';
import 'package:memeory/pages/about/about.dart';
import 'package:memeory/pages/memes/memes_horizontal.dart';
import 'package:memeory/pages/memes/memes_vertical.dart';
import 'package:memeory/pages/preferences/widgets/channels.dart';
import 'package:memeory/pages/preferences/widgets/orientations.dart';
import 'package:memeory/pages/preferences/widgets/socials.dart';
import 'package:memeory/pages/preferences/widgets/wrapper.dart';
import 'package:memeory/strings/ru.dart';
import 'package:memeory/util/consts.dart';
import 'package:memeory/util/theme.dart';

class MemesPage extends StatelessWidget {
  const MemesPage({
    this.orientation = MemesOrientation.VERTICAL,
  });

  final MemesOrientation orientation;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: PreferredSize(
        preferredSize: Size.fromHeight(50),
        child: AppBar(
          centerTitle: true,
          title: Text(MEMES),
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
              decoration: BoxDecoration(
                color: Theme.of(context).primaryColorLight,
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
                onChanged: (value) {
                  Navigator.pop(context);
                  return changeTheme(value, context);
                },
              ),
            ),
          ],
        ),
      ),
      body: orientation == MemesOrientation.VERTICAL
          ? MemesVertical()
          : MemesHorizontal(),
      backgroundColor: Theme.of(context).primaryColorDark,
    );
  }

  void pushToPrefs({BuildContext context, Widget body, Future apply}) {
    Navigator.pop(context);
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (pageContext) => Scaffold(
          appBar: AppBar(),
          body: PreferencesPageWrapper(
            apply: () async {
              if (apply != null) await apply;
              Navigator.pop(pageContext);
            },
            applyText: BACK_TO_WATCH_MEMES,
            child: body,
          ),
        ),
      ),
    );
  }
}
