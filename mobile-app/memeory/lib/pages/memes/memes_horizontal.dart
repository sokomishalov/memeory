import 'package:flutter/material.dart';
import 'package:memeory/api/memes.dart';
import 'package:memeory/components/containers/future_builder.dart';
import 'package:memeory/theme/dark.dart';
import 'package:memeory/theme/light.dart';
import 'package:memeory/theme/theme.dart';

import 'memes_mixin.dart';

class MemesHorizontal extends StatefulWidget {
  @override
  _MemesHorizontalState createState() => _MemesHorizontalState();
}

class _MemesHorizontalState extends State<MemesHorizontal> with MemesMixin {
  PageController _controller;
  int _currentPage;
  Future<List> _memes;

  @override
  void initState() {
    _controller = new PageController();
    _currentPage = 0;
    _memes = fetchMemes(_currentPage);
    super.initState();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return FutureWidget(
      future: _memes,
      render: (memes) => PageView.builder(
        itemCount: memes?.length ?? 0,
        controller: _controller,
        itemBuilder: (context, index) {
          var item = memes[index] ?? {};

          return Container(
            key: Key(item["id"]),
            decoration: BoxDecoration(
              color: dependingOnThemeChoice(
                context: context,
                light: MEME_BACKGROUND_COLOR_LIGHT,
                dark: MEME_BACKGROUND_COLOR_DARK,
              ),
            ),
            child: Center(
              child: Container(
                child: ListView(
                  shrinkWrap: true,
                  children: <Widget>[
                    prepareHeader(item, context),
                    prepareCaption(item, context),
                    ...prepareAttachments(item)
                  ],
                ),
              ),
            ),
          );
        },
      ),
    );
  }
}
