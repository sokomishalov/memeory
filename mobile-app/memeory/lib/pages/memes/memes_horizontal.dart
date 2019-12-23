import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:memeory/pages/memes/memes_screen_args.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:memeory/util/theme/dark.dart';
import 'package:memeory/util/theme/light.dart';
import 'package:memeory/util/theme/theme.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';

import 'memes_mixin.dart';

class MemesHorizontal extends StatefulWidget {
  const MemesHorizontal({Key key, this.screenArgs}) : super(key: key);

  final MemesScreenArgs screenArgs;

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
      controller: refreshController,
      onRefresh: () => onRefresh(widget.screenArgs),
      onLoading: () => onLoading(widget.screenArgs),
      header: ClassicHeader(
        releaseText: "",
        refreshingText: "",
        completeText: "",
        idleText: "",
        failedText: t(context, "error_loading_memes"),
        idleIcon: const Icon(Icons.chevron_right, color: Colors.grey),
      ),
      footer: buildLoaderFooter(),
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
                    buildMemeHeader(context, item),
                    buildMemeCaption(item),
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
