import 'package:flutter/material.dart';
import 'package:memeory/common/containers/future_builder.dart';
import 'package:memeory/pages/memes/memes.dart';
import 'package:memeory/pages/preferences/preferences.dart';
import 'package:memeory/util/storage.dart';

class HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return FutureWidget(
      future: isFirstAppVisit(),
      render: (dynamic data) => data ? UserPreferencesPage() : MemesPage(),
    );
  }
}
