import 'package:flutter/material.dart';
import 'package:memeory/util/i18n/i18n.dart';

class BottomSheet extends StatelessWidget {
  const BottomSheet({
    Key key,
    this.children,
    this.close,
  })  : assert(children != null && children.length > 0),
        super(key: key);

  final List<BottomSheetItem> children;
  final VoidCallback close;

  @override
  Widget build(BuildContext context) {
    var items = <Widget>[];

    if (close == null) {
      items = children;
    } else {
      items = [
        ...children,
        BottomSheetItem(
          caption: t(context, "close"),
          icon: Icon(Icons.close),
          onPressed: close,
        ),
      ];
    }

    return Container(
      height: (items.length * 56).toDouble() + 20,
      child: Column(
        children: items,
      ),
    );
  }
}

class BottomSheetItem extends StatelessWidget {
  const BottomSheetItem({
    Key key,
    this.caption,
    this.icon,
    this.onPressed,
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

Future showMemeoryBottomSheet({
  @required BuildContext context,
  @required List<BottomSheetItem> children,
}) async {
  return await showModalBottomSheet(
    context: context,
    builder: (BuildContext context) {
      return BottomSheet(
        children: children,
        close: () {
          Navigator.pop(context);
        },
      );
    },
  );
}
