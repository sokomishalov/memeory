import 'package:flutter/material.dart';
import 'package:memeory/api/profile.dart';
import 'package:memeory/cache/repository/orientations_repo.dart';
import 'package:memeory/cache/repository/visits_repo.dart';
import 'package:memeory/pages/memes/memes.dart';
import 'package:memeory/pages/preferences/widgets/channels.dart';
import 'package:memeory/pages/preferences/widgets/orientations.dart';
import 'package:memeory/pages/preferences/widgets/socials.dart';
import 'package:memeory/pages/preferences/widgets/themes.dart';
import 'package:memeory/pages/preferences/widgets/wrapper.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:page_transition/page_transition.dart';
import 'package:preload_page_view/preload_page_view.dart';

class UserPreferencesPage extends StatelessWidget {
  final _controller = PreloadPageController();

  void _nextPage() {
    _controller.nextPage(
      duration: Duration(milliseconds: 300),
      curve: Curves.easeInOut,
    );
  }

  void _close(context) async {
    await saveProfile();

    await setAppVisitDatetime();
    var orientation = await getPreferredOrientation();

    Navigator.of(context).pushReplacement(
      PageTransition(
        type: PageTransitionType.upToDown,
        child: MemesPage(orientation: orientation),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: PreloadPageView(
        controller: _controller,
        children: [
          PreferencesPageWrapper(
            title: t(context, "choose_theme"),
            nextPage: _nextPage,
            child: ThemePreferences(),
          ),
          PreferencesPageWrapper(
            title: t(context, "please_authorize"),
            nextPage: _nextPage,
            child: SocialPreferences(),
          ),
          PreferencesPageWrapper(
            title: t(context, "choose_orientation"),
            nextPage: _nextPage,
            child: OrientationPreferences(),
          ),
          PreferencesPageWrapper(
            title: t(context, "choose_channels"),
            apply: () => _close(context),
            applyText: t(context, "start_watching_memes"),
            child: ChannelPreferences(),
          )
        ],
      ),
    );
  }
}
