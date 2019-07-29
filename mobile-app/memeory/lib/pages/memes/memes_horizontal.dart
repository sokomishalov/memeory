import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:memeory/theme/dark.dart';
import 'package:memeory/theme/light.dart';
import 'package:memeory/theme/theme.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';

import 'memes_mixin.dart';

class MemesHorizontal extends StatefulWidget {
  @override
  _MemesHorizontalState createState() => _MemesHorizontalState();
}

class _MemesHorizontalState extends State<MemesHorizontal> with MemesMixin {
  PageController _pageController;

  @override
  void initState() {
    _pageController = PageController();
    super.initState();
  }

  @override
  void dispose() {
    _pageController?.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return SmartRefresher(
      enablePullDown: true,
      enablePullUp: true,
      header: buildLoaderHeader(),
      footer: buildLoaderFooter(),
      controller: refreshController,
      onRefresh: onRefresh,
      onLoading: onLoading,
      child: ListView.builder(
        shrinkWrap: true,
        scrollDirection: Axis.horizontal,
        controller: _pageController,
        physics: PageScrollPhysics(),
        itemCount: memes?.length ?? 0,
        itemBuilder: (context, index) {
          var item = memes[index] ?? {};

          return Container(
            width: MediaQuery.of(context).size.width,
            key: Key(item["id"]),
            decoration: BoxDecoration(
              color: dependingOnThemeChoice(
                context: context,
                light: MEME_BACKGROUND_COLOR_LIGHT,
                dark: MEME_BACKGROUND_COLOR_DARK,
              ),
            ),
            child: Center(
              child: SingleChildScrollView(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    buildMemeHeader(item, context),
                    buildMemeCaption(item, context),
                    ...buildMemeAttachments(item),
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
