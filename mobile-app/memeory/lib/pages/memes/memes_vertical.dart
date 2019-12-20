import 'dart:math';

import 'package:flutter/material.dart';
import 'package:memeory/components/containers/loader.dart';
import 'package:memeory/pages/memes/attachments/carousel_slider.dart';
import 'package:memeory/util/collections/collections.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:memeory/util/theme/dark.dart';
import 'package:memeory/util/theme/light.dart';
import 'package:memeory/util/theme/theme.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';

import 'memes_mixin.dart';

class MemesVertical extends StatefulWidget {
  @override
  _MemesVerticalState createState() => _MemesVerticalState();
}

class _MemesVerticalState extends State<MemesVertical> with MemesMixin {
  @override
  Widget build(BuildContext context) {
    return SmartRefresher(
      enablePullDown: true,
      enablePullUp: true,
      controller: refreshController,
      onRefresh: onRefresh,
      onLoading: onLoading,
      header: WaterDropHeader(
        completeDuration: Duration(milliseconds: 400),
        refresh: SizedBox(
          width: 25,
          height: 25,
          child: Loader(),
        ),
        complete: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.done, color: Colors.grey),
            Container(width: 15.0)
          ],
        ),
        failed: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.close, color: Colors.grey),
            Container(width: 15.0),
            Text(
              t(context, "error_loading_memes"),
              style: TextStyle(color: Colors.grey),
            )
          ],
        ),
      ),
      footer: buildLoaderFooter(),
      child: ListView.builder(
        shrinkWrap: true,
        itemCount: memes?.length ?? 0,
        itemBuilder: (context, index) {
          var item = memes[index] ?? {};

          return Container(
            key: Key(item["id"]),
            margin: EdgeInsets.only(bottom: 15),
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
                buildMemeHeader(item, context),
                buildMemeCaption(item, context),
                AttachmentCarousel(
                  minAspectRatio: isNotEmpty(item["attachments"])
                      ? item["attachments"]
                          ?.map((a) => a["aspectRatio"])
                          ?.cast<double>()
                          ?.reduce((double o1, double o2) =>
                              (o1 != null && o2 != null) ? min(o1, o2) : 1.0)
                      : 1,
                  items: buildMemeAttachments(item),
                )
              ],
            ),
          );
        },
      ),
    );
  }
}
