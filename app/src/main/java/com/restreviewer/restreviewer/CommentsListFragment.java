package com.restreviewer.restreviewer;

import android.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.restreviewer.restreviewer.Models.Comment;
import com.restreviewer.restreviewer.Models.Model;

import java.util.List;

public class CommentsListFragment extends ListFragment {

    List<Comment> comments;

    public CommentsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_comments_list, container, false);

        final ListView listview = (ListView) view.findViewById(android.R.id.list);

        // Inflate the layout for this fragment
        String restaurantId = this.getArguments().getString("restaurantId");
        Model.instance().getComments(Integer.parseInt(restaurantId), new Model.GetCommentsListener() {
            @Override
            public void done(final List<Comment> commentsRes) {
                comments = commentsRes;

                final ListAdapter adapter = new ListAdapter(MyApplication.getContext(),
                        R.layout.comments_list_item, comments);
                listview.setAdapter(adapter);
            }
        });

        return view;
    }

    public class ListAdapter extends ArrayAdapter<Comment> {
        public ListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public ListAdapter(Context context, int resource, List<Comment> items) {
            super(context, resource, items);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.comments_list_item, null);
            }

            Comment comment = getItem(position);

            if (comment != null) {
                TextView title = (TextView) v.findViewById(R.id.comment_title);
                TextView content = (TextView) v.findViewById(R.id.comment_text);
                TextView rate = (TextView) v.findViewById(R.id.comment_rate_number);

                if (title != null) {
                    title.setText(comment.getTitle());
                }

                if (content != null) {
                    content.setText(comment.getContent());
                }

                if (rate != null){
                    rate.setText(comment.getRate().toString());
                }
            }

            return v;
        }

    }

    }
