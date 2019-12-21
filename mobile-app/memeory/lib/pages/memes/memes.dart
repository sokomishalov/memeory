import 'package:flutter/material.dart';
import 'package:memeory/model/scrolling_axis.dart';
import 'package:memeory/pages/appbar/appbar.dart';
import 'package:memeory/pages/drawer/drawer.dart';
import 'package:memeory/pages/memes/memes_horizontal.dart';
import 'package:memeory/pages/memes/memes_vertical.dart';
import 'package:memeory/util/theme/dark.dart';
import 'package:memeory/util/theme/light.dart';
import 'package:memeory/util/theme/theme.dart';

class MemesPage extends StatelessWidget {
  const MemesPage({
    this.orientation = ScrollingAxis.VERTICAL,
  });

  final ScrollingAxis orientation;

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
        child: MemeoryAppBar(),
      ),
      drawer: CustomDrawer(),
      body: orientation == ScrollingAxis.VERTICAL
          ? MemesVertical()
          : MemesHorizontal(),
    );
  }
}
