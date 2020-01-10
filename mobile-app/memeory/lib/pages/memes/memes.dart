import 'package:flutter/material.dart';
import 'package:memeory/cache/repository/scrolling_axis_repo.dart';
import 'package:memeory/components/containers/future_builder.dart';
import 'package:memeory/model/scrolling_axis.dart';
import 'package:memeory/pages/drawer/drawer.dart';
import 'package:memeory/pages/memes/memes_horizontal.dart';
import 'package:memeory/pages/memes/memes_screen_args.dart';
import 'package:memeory/pages/memes/memes_vertical.dart';
import 'package:memeory/util/theme/dark.dart';
import 'package:memeory/util/theme/light.dart';
import 'package:memeory/util/theme/theme.dart';

import 'memes_app_bar.dart';

class MemesPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final MemesScreenArgs args =
        ModalRoute.of(context).settings.arguments ?? MemesScreenArgs();

    return Scaffold(
      backgroundColor: dependingOnThemeChoice(
        context: context,
        light: MEME_BACKGROUND_COLOR_LIGHT,
        dark: MEME_BACKGROUND_COLOR_DARK,
      ),
      appBar: PreferredSize(
        preferredSize: Size.fromHeight(50),
        child: MemesAppBar(
          screenArgs: args,
        ),
      ),
      drawer: CustomDrawer(),
      body: FutureWidget(
        future: getPreferredScrollingAxis(),
        render: (axis) {
          return axis == ScrollingAxis.VERTICAL
              ? MemesVertical(screenArgs: args)
              : MemesHorizontal(screenArgs: args);
        },
      ),
    );
  }
}
