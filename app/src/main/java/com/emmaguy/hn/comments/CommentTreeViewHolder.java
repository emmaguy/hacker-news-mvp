package com.emmaguy.hn.comments;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.emmaguy.hn.R;
import com.emmaguy.hn.model.Comment;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by emma on 22/03/15.
 */
public class CommentTreeViewHolder extends TreeNode.BaseNodeViewHolder<Comment> {
    private final LayoutInflater mLayoutInflater;

    public CommentTreeViewHolder(Context context) {
        super(context);

        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    @SuppressWarnings("InflateParams")
    public View createNodeView(TreeNode treeNode, Comment comment) {
        final View view = mLayoutInflater.inflate(R.layout.row_comment_tree_node, null, false);
        TextView textView = (TextView) view.findViewById(R.id.row_comment_textview_text);
        textView.setText(Html.fromHtml(comment.getText()));

        return view;
    }
}
