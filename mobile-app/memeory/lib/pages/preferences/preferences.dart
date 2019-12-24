import 'package:flutter/material.dart';
import 'package:memeory/cache/repository/scrolling_axis_repo.dart';
import 'package:memeory/cache/repository/visits_repo.dart';
import 'package:memeory/pages/appearance/appearance.dart';
import 'package:memeory/pages/memes/memes.dart';
import 'package:memeory/pages/memes/memes_screen_args.dart';
import 'package:memeory/pages/preferences/preferences_wrapper.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:memeory/util/routes/routes.dart';
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
    await setAppVisitDatetime();
    var scrollingAxis = await getPreferredScrollingAxis();

    Navigator.pushReplacementNamed(
      context,
      ROUTES.MEMES.route,
      arguments: MemesScreenArgs(scrollingAxis: scrollingAxis),
      result: PageTransition(
        type: PageTransitionType.upToDown,
        child: MemesPage(),
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
            child: AppearancePreferences(),
            apply: () => _close(context),
            applyText: t(context, "start_watching_memes"),
          ),
        ],
      ),
    );
  }
}
