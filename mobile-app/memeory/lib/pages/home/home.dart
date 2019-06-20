import 'package:flutter/material.dart';
import 'package:memeory/cache/repository/orientations_repo.dart';
import 'package:memeory/cache/repository/visits_repo.dart';
import 'package:memeory/common/containers/future_builder.dart';
import 'package:memeory/model/orientation.dart';
import 'package:memeory/pages/memes/memes_horizontal.dart';
import 'package:memeory/pages/memes/memes_vertical.dart';
import 'package:memeory/pages/preferences/preferences.dart';

class HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return FutureWidget(
      future: isFirstAppVisit(),
      render: (isFirstApp) => isFirstApp
          ? UserPreferencesPage()
          : FutureWidget(
              future: getPreferredOrientation(),
              render: (orientation) => orientation == MemesOrientation.VERTICAL
                  ? MemesVertical()
                  : MemesHorizontal(),
            ),
    );
  }
}
