import 'package:flutter/material.dart';
import 'package:flutter/src/cupertino/icons.dart' show CupertinoIcons;
import 'package:loadmore/loadmore.dart';
import 'package:memeory/api/memes.dart';
import 'package:memeory/common/message/messages.dart';
import 'package:memeory/pages/memes/attachments/photo.dart';
import 'package:memeory/pages/memes/attachments/video.dart';

class MemesVertical extends StatefulWidget {
  @override
  _MemesVerticalState createState() => _MemesVerticalState();
}

class _MemesVerticalState extends State<MemesVertical> {
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
          var item = _memes[index];
          var attachments = item["attachments"]?.map((a) {
            if (a["type"] == "IMAGE") {
              return PhotoAttachment(url: a["url"]);
            } else if (a["type"] == "VIDEO") {
              return VideoAttachment(url: a["url"]);
            } else {
              return Container();
            }
          })?.toList();

          return Container(
            key: Key(item["id"]),
            margin: EdgeInsets.symmetric(vertical: 10),
            padding: EdgeInsets.only(bottom: 25),
            decoration: BoxDecoration(
              color: Theme.of(context).primaryColorLight,
            ),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Container(
                  padding: EdgeInsets.only(
                    left: 10,
                    top: 10,
                    right: 15,
                    bottom: 10,
                  ),
                  child: Row(
                    children: <Widget>[
                      Container(
                        child: Icon(
                          Icons.insert_emoticon,
                          color: Theme.of(context).accentColor,
                        ),
                        padding: EdgeInsets.all(3),
                        decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(15),
                          border: Border.all(
                            width: 1,
                            color: Theme.of(context).accentColor,
                          ),
                        ),
                      ),
                      Spacer(flex: 1),
                      Text(
                        item["channel"],
                        style: TextStyle(fontSize: 12),
                      ),
                      Spacer(flex: 20),
                      GestureDetector(
                        onTap: onTapEllipsis,
                        child: Icon(CupertinoIcons.ellipsis),
                      ),
                    ],
                  ),
                ),
                Container(
                  padding: EdgeInsets.only(left: 10, right: 10, bottom: 10),
                  child: Text(
                    item["caption"],
                    style: TextStyle(fontSize: 16),
                  ),
                ),
                ...attachments
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

  void onTapEllipsis() {
    infoToast("Пока не реализовано", context);
  }

  void onAppBarTap() {
    infoToast("Пока не реализовано", context);
  }
}
