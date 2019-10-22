import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:memeory/util/i18n/i18n.dart';

class BottomSheet extends StatelessWidget {
  const BottomSheet({
    Key key,
    this.items,
    this.close(),
  })  : assert(items != null && items.length > 0),
        assert(close != null),
        super(key: key);

  final List<BottomSheetItem> items;
  final VoidCallback close;

  @override
  Widget build(BuildContext context) {
    var widgets = <Widget>[];

    items.forEach((o) {
      widgets.add(BottomSheetWidget(
        caption: o.caption,
        icon: o.icon,
        onPressed: () {
          o.onPressed();
          close();
        },
      ));
    });

    widgets.add(BottomSheetWidget(
      caption: t(context, "close"),
      icon: Icon(FontAwesomeIcons.solidWindowClose),
      onPressed: close,
    ));

    return Container(
      height: (widgets.length * 56).toDouble() + 20,
      child: Column(children: widgets),
    );
  }
}

Future showMemeoryBottomSheet({
  @required BuildContext context,
  @required List<BottomSheetItem> items,
}) async {
  return await showModalBottomSheet(
    context: context,
    builder: (BuildContext context) {
      return BottomSheet(
        items: items,
        close: () {
          Navigator.pop(context);
        },
      );
    },
  );
}

class BottomSheetWidget extends StatelessWidget {
  const BottomSheetWidget({
    Key key,
    @required this.caption,
    @required this.icon,
    @required this.onPressed,
  }) : super(key: key);

  final String caption;
  final Icon icon;
  final VoidCallback onPressed;

  @override
  Widget build(BuildContext context) {
    return Container(
      child: ListTile(
        leading: icon,
        title: Text(caption),
        onTap: onPressed,
      ),
    );
  }
}

class BottomSheetItem {
  const BottomSheetItem({
    @required this.caption,
    @required this.icon,
    @required this.onPressed,
  });

  final String caption;
  final Icon icon;
  final VoidCallback onPressed;
}
