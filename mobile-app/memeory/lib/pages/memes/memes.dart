import 'package:flutter/material.dart';
import 'package:memeory/components/app_bar/app_bar.dart';
import 'package:memeory/model/orientation.dart';
import 'package:memeory/pages/memes/memes_horizontal.dart';
import 'package:memeory/pages/memes/memes_vertical.dart';
import 'package:memeory/strings/ru.dart';
import 'package:memeory/theme/dark.dart';
import 'package:memeory/theme/light.dart';
import 'package:memeory/theme/theme.dart';

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
      appBar: buildAppBar(MEMES),
      body: orientation == MemesOrientation.VERTICAL
          ? MemesVertical()
          : MemesHorizontal(),
    );
  }
}
