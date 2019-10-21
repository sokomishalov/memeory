import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:memeory/api/channels.dart';
import 'package:memeory/api/memes.dart';
import 'package:memeory/api/profile.dart';
import 'package:memeory/cache/repository/channels_repo.dart';
import 'package:memeory/components/bottom_sheet/bottom_sheet.dart';
import 'package:memeory/components/containers/loader.dart';
import 'package:memeory/components/images/channel_logo.dart';
import 'package:memeory/model/scrolling_axis.dart';
import 'package:memeory/util/collections/collections.dart';
import 'package:memeory/util/consts/consts.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:memeory/util/time/time.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';

import 'attachments/image.dart';
import 'attachments/video.dart';

mixin MemesMixin<T extends StatefulWidget> on State<T> {
  int _currentPage;
  List memes;
  RefreshController refreshController;

  @override
  void initState() {
    _currentPage = -1;
    memes = [];
    refreshController = RefreshController(initialRefresh: true);
    super.initState();
  }

  @override
  void dispose() {
    refreshController?.dispose();
    super.dispose();
  }

  Future<void> onRefresh() async {
    await _loadMore(0);
    refreshController.refreshCompleted();
  }

  Future<void> onLoading() async {
    await _loadMore(_currentPage + 1);
    refreshController.loadComplete();
  }

  Future<void> _loadMore(int page) async {
    var newMemes = await fetchMemes(page);

    setState(() {
      _currentPage = page;
      memes = distinctByProperty([...memes, ...newMemes], "id");
    });
  }

  Widget buildMemeHeader(item, context) {
    return Container(
      padding: EdgeInsets.only(
        left: 10,
        top: 10,
        right: 15,
        bottom: 10,
      ),
      child: Row(
        children: <Widget>[
          ChannelLogo(channelId: item["channelId"]),
          Spacer(flex: 1),
          Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              Text(
                item["channelName"],
                style: TextStyle(fontSize: 12),
              ),
              Container(
                padding: EdgeInsets.only(top: 4),
                child: Opacity(
                  child: Text(
                    timeAgo(context, item["publishedAt"]),
                    style: TextStyle(fontSize: 12),
                  ),
                  opacity: 0.5,
                ),
              ),
            ],
          ),
          Spacer(flex: 20),
          GestureDetector(
            onTap: () => onTapEllipsis(context, item),
            child: Icon(CupertinoIcons.ellipsis),
          ),
        ],
      ),
    );
  }

  Widget buildMemeCaption(item, context) {
    return Container(
      padding: EdgeInsets.only(left: 10, right: 10, bottom: 10),
      child: Text(
        item["caption"] ?? EMPTY,
        softWrap: true,
        style: TextStyle(fontSize: 16),
      ),
    );
  }

  List<Widget> buildMemeAttachments(item) {
    return item["attachments"]
            ?.map((a) {
              var aspectRatio = a["aspectRatio"];
              var url = a["url"];
              var type = a["type"];

              if (type == IMAGE_ATTACHMENT_TYPE) {
                return ImageAttachment(
                  url: url,
                  aspectRatio: aspectRatio,
                );
              } else if (type == VIDEO_ATTACHMENT_TYPE) {
                return VideoAttachment(
                  url: url,
                  aspectRatio: aspectRatio,
                );
              } else {
                return Container();
              }
            })
            ?.cast<Widget>()
            ?.toList() ??
        [];
  }

  Widget buildLoaderHeader(ScrollingAxis orientation) {
    return orientation == ScrollingAxis.HORIZONTAL
        ? ClassicHeader(
            releaseText: EMPTY,
            refreshingText: EMPTY,
            completeText: EMPTY,
            idleText: EMPTY,
            failedText: t(context, "error_loading_memes"),
            idleIcon: const Icon(Icons.chevron_right, color: Colors.grey),
          )
        : WaterDropHeader(
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
                Text(t(context, "error_loading_memes"),
                    style: TextStyle(color: Colors.grey))
              ],
            ),
          );
  }

  Widget buildLoaderFooter() {
    return CustomFooter(
      builder: (BuildContext context, LoadStatus mode) {
        Widget widget;
        switch (mode) {
          case LoadStatus.idle:
          case LoadStatus.canLoading:
          case LoadStatus.loading:
            widget = Text(t(context, "loading_memes"));
            break;
          case LoadStatus.noMore:
            widget = Text(t(context, "no_more_memes"));
            break;
          case LoadStatus.failed:
            widget = Text(t(context, "error_loading_memes"));
            break;
        }

        return Container(
          height: 55.0,
          child: Center(child: widget),
        );
      },
    );
  }

  void onTapEllipsis(BuildContext context, Map<String, dynamic> item) {
    showMemeoryBottomSheet(
      context: context,
      children: [
        BottomSheetItem(
          caption: t(context, "remove_channel"),
          icon: Icon(Icons.report),
          onPressed: () async {
            final watchAll = await getWatchAll();

            if (watchAll) {
              var channelsToSave = (await fetchChannels())
                  .map((it) => it["id"])
                  .where((it) => it != item["channelId"])
                  .toList();

              await setChannels(channelsToSave);
              await setWatchAll(false);
            } else {
              await removeChannel(item["channelId"]);
            }

            await saveProfile();

            Navigator.pop(context);

            setState(() {
              _currentPage = -1;
              memes = [];
            });

            await onRefresh();
          },
        )
      ],
    );
  }
}
