import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:memeory/api/memes.dart';
import 'package:memeory/components/bottom_sheet/bottom_sheet.dart';
import 'package:memeory/components/images/channel_logo.dart';
import 'package:memeory/components/message/messages.dart';
import 'package:memeory/util/consts/consts.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:memeory/util/time/time.dart';
import 'package:pull_to_refresh/pull_to_refresh.dart';
import 'package:share/share.dart';

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
    var newMemes = await fetchMemes(pageNumber: page);

    setState(() {
      _currentPage = page;
      memes = memes + newMemes;
    });
  }

  Widget buildMemeHeader(BuildContext context, Map item) {
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

  Widget buildMemeCaption(Map item) {
    return Container(
      padding: EdgeInsets.only(left: 10, right: 10, bottom: 10),
      child: Text(
        item["caption"] ?? EMPTY,
        softWrap: true,
        style: TextStyle(fontSize: 16),
      ),
    );
  }

  List<Widget> buildMemeAttachments(Map item) {
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
      items: [
        BottomSheetItem(
          caption: t(context, "share_meme"),
          icon: Icon(FontAwesomeIcons.shareAlt),
          onPressed: () async {
            await _shareMeme(item);
          },
        ),
        BottomSheetItem(
          caption: t(context, "report_abuse"),
          icon: Icon(FontAwesomeIcons.lock),
          onPressed: () {
            _reportAbuse(context);
          },
        )
      ],
    );
  }

  _reportAbuse(BuildContext context) {
    errorToast(context, t(context, "report_your_ass"));
  }

  Future _shareMeme(Map<String, dynamic> item) async {
    var url = getMemeShareUrl(item["id"]);
    Share.share(url);
    await onRefresh();
  }
}
