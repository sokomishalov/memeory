import 'package:flutter/material.dart';
import 'package:memeory/cache/repository/orientations_repo.dart';
import 'package:memeory/cache/repository/visits_repo.dart';
import 'package:memeory/pages/memes/memes.dart';
import 'package:memeory/pages/preferences/widgets/channels.dart';
import 'package:memeory/pages/preferences/widgets/orientations.dart';
import 'package:memeory/pages/preferences/widgets/socials.dart';
import 'package:memeory/pages/preferences/widgets/themes.dart';
import 'package:memeory/pages/preferences/widgets/wrapper.dart';
import 'package:memeory/strings/ru.dart';

class UserPreferencesPage extends StatelessWidget {
  final _controller = PageController();

  void nextPage() {
    _controller.nextPage(
      duration: Duration(milliseconds: 300),
      curve: Curves.easeInOut,
    );
  }

  void close(context) async {
    await setAppVisitDatetime();
    var orientation = await getPreferredOrientation();

    Navigator.of(context).pushReplacement(
      MaterialPageRoute(
        builder: (context) => MemesPage(orientation: orientation),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: PageView(
        controller: _controller,
        children: <Widget>[
          PreferencesPageWrapper(
            title: PLEASE_AUTHORIZE,
            nextPage: nextPage,
            child: SocialPreferences(),
          ),
          PreferencesPageWrapper(
            title: CHOOSE_THEME,
            nextPage: nextPage,
            child: ThemePreferences(),
          ),
          PreferencesPageWrapper(
            title: CHOOSE_ORIENTATION,
            nextPage: nextPage,
            child: OrientationPreferences(),
          ),
          PreferencesPageWrapper(
            title: CHOOSE_CHANNELS,
            apply: () => close(context),
            applyText: START_WATCHING_MEMES,
            child: ChannelPreferences(),
          )
        ],
      ),
    );
  }
}
