import 'dart:math';

import 'package:flutter/material.dart';
import 'package:loadmore/loadmore.dart';
import 'package:memeory/api/memes.dart';
import 'package:memeory/pages/memes/attachments/carousel_slider.dart';
import 'package:memeory/pages/memes/mixin.dart';
import 'package:memeory/theme/dark.dart';
import 'package:memeory/theme/light.dart';
import 'package:memeory/theme/theme.dart';

class MemesVertical extends StatefulWidget {
  @override
  _MemesVerticalState createState() => _MemesVerticalState();
}

class _MemesVerticalState extends State<MemesVertical> with MemesMixin {
  int _currentPage;
  List _memes;

  @override
  void initState() {
    _currentPage = -1;
    _memes = [];
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return LoadMore(
      textBuilder: loadingTextBuilder,
      delegate: DefaultLoadMoreDelegate(),
      onLoadMore: onLoadMore,
      child: ListView.builder(
        itemCount: _memes?.length ?? 0,
        itemBuilder: (context, index) {
          var item = _memes[index] ?? {};

          return Container(
            key: Key(item["id"]),
            margin: EdgeInsets.only(bottom: 20),
            decoration: BoxDecoration(
              color: dependingOnThemeChoice(
                context: context,
                light: MEME_BACKGROUND_COLOR_LIGHT,
                dark: MEME_BACKGROUND_COLOR_DARK,
              ),
            ),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                prepareHeader(item, context),
                prepareCaption(item, context),
                AttachmentCarousel(
                  minAspectRatio: item["attachments"]
                          ?.map((a) => a["aspectRatio"])
                          ?.cast<double>()
                          ?.reduce((double o1, double o2) =>
                              o1 != null && o2 != null ? min(o1, o2) : 1.0) ??
                      1.0,
                  items: prepareAttachments(item),
                )
              ],
            ),
          );
        },
      ),
    );
  }

  Future<bool> onLoadMore() async {
    var newPage = _currentPage + 1;
    var newMemes = List.from(_memes)..addAll(await fetchMemes(newPage));

    setState(() {
      _currentPage = newPage;
      _memes = newMemes;
    });

    return true;
  }

  String loadingTextBuilder(LoadMoreStatus status) {
    String text;
    switch (status) {
      case LoadMoreStatus.fail:
        text = "Ошибка при загрузке мемесов :(";
        break;
      case LoadMoreStatus.idle:
      case LoadMoreStatus.loading:
        text = "Подождите, гружу мемесы ...";
        break;
      case LoadMoreStatus.nomore:
      default:
        text = "Нет больше мемесов :(";
        break;
    }
    return text;
  }
}
