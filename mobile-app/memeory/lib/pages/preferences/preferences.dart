import 'package:flutter/material.dart';
import 'package:memeory/cache/repository/orientations_repo.dart';
import 'package:memeory/cache/repository/visits_repo.dart';
import 'package:memeory/model/orientation.dart';
import 'package:memeory/pages/memes/memes_horizontal.dart';
import 'package:memeory/pages/memes/memes_vertical.dart';
import 'package:memeory/pages/preferences/widgets/channels.dart';
import 'package:memeory/pages/preferences/widgets/orientations.dart';
import 'package:memeory/pages/preferences/widgets/socials.dart';
import 'package:memeory/pages/preferences/widgets/themes.dart';
import 'package:memeory/pages/preferences/widgets/wrapper.dart';

class UserPreferencesPage extends StatelessWidget {
  final _controller = PageController();

  void nextPage() {
    _controller.nextPage(
      duration: Duration(milliseconds: 300),
      curve: Curves.easeInOut,
    );
  }

  void previousPage() {
    _controller.previousPage(
      duration: Duration(milliseconds: 300),
      curve: Curves.easeInOut,
    );
  }

  void close(context) async {
    await setAppVisitDatetime();
    var orientation = await getPreferredOrientation();

    Navigator.of(context).pushReplacement(
      MaterialPageRoute(
        builder: (context) => orientation == MemesOrientation.VERTICAL
            ? MemesVertical()
            : MemesHorizontal(),
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
            title: "Выберите тему приложения!",
            nextPage: nextPage,
            child: ThemePreferences(),
          ),
          PreferencesPageWrapper(
            title: "Выберите, как вам удобнее будет орать с мемов!",
            previousPage: previousPage,
            nextPage: nextPage,
            child: OrientationPreferences(),
          ),
          PreferencesPageWrapper(
            title: "Выберите источники мемов, которые Вам интересны!",
            nextPage: nextPage,
            child: ChannelPreferences(),
          ),
          PreferencesPageWrapper(
            title: "Авторизуйтесь в соцсетях!",
            previousPage: previousPage,
            close: () => close(context),
            child: SocialPreferences(),
          ),
        ],
      ),
    );
  }
}
