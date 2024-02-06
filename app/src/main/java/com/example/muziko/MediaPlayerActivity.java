package com.example.muziko;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MediaPlayerActivity extends AppCompatActivity {

    TextView txtTitle, txtTimeSong, txtTimeTotal;
    SeekBar sbSong;
    ImageButton btnPrev, btnPlay, btnRestart, btnForward;
    ImageView imgDisc;
    ArrayList<Song> arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);

        AnhXa();
        AddSong();

        animation = AnimationUtils.loadAnimation(this,R.anim.disc_rotate);
        KhoiTaoMediaPlayer(); //Hàm lấy bài hát từ trong danh sách

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lùi về bài hát trước đó
                position--;
                //Kiểm tra nếu index vị trí nhỏ hơn số lượng bài hát, trả về bài hát cuối cùng của ds
                if(position < 0){
                    position = arraySong.size() -1;
                }
                /*Để tránh hiện tượng nhạc phát chồng nhau khi bấm Next, thực hiện kiểm tra bài hát có đang phát hay không
                nếu có thì cho dừng bài hát */
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause64);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });
        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Đi đến bài hát tiếp theo
                position++;
                //Kiểm tra nếu index vị trí lớn hơn số lượng bài hát trong ds, trả index về vị trí ban đầu
                if(position >arraySong.size() - 1){
                    position = 0;
                }
                /*Để tránh hiện tượng nhạc phát chồng nhau khi bấm Next, thực hiện kiểm tra bài hát có đang phát hay không
                nếu có thì cho dừng bài hát */
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause64);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });
        btnRestart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                //Dừng hoàn toàn
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.play64);
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause64);
            }

        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ki
                if (mediaPlayer.isPlaying()) {
                    //nếu đang phát -> pause -> đổi sang hình play
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play64);

                } else {
                    // đang ngừng -> phát -> đổi sang hình pause
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause64);
                }
                SetTimeTotal();
                UpdateTimeSong();
                imgDisc.startAnimation(animation);
            }
        });
        //Hàm để người dùng tương tác với  SeekBar trên mỗi bài hát.
        sbSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(sbSong.getProgress());
            }
        });
    }

    private void UpdateTimeSong() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() { //Cập nhật thời gian
                //Lấy thời gian hiện tại, sau đó cập nhật định dạng Phút:Giây(mm:ss) cho text đầu tiên
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));
                // update progress sbSong
                //Để lặp lại hành động cập nhật trên, ta gọi lại hàm postDelayed từ handler
                sbSong.setProgress(mediaPlayer.getCurrentPosition());

                //Kiểm tra thời gian bài hát  -> nếu kết thúc -> Next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if(position >arraySong.size() - 1){
                            position = 0;
                        }

                        if(mediaPlayer.isPlaying())
                        {
                            mediaPlayer.stop();
                        }
                        KhoiTaoMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause64);
                        SetTimeTotal();
                        UpdateTimeSong();
                    }
                });
                handler.postDelayed(this, 500);
            }
        },100 );
    }
    private void SetTimeTotal() {
        //Định dạng kiểu hiển thị thời gian cho SeekBar, ta dùng function SimpleDateFormat được cung cấp sẵn trong Java
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss"); //Định dạng thời gian hiển thị kiểu Phút:Giây
        txtTimeTotal.setText(dinhDangGio.format(mediaPlayer.getDuration()));
        // gán max của skSong = mediaPlayer.getDuration()
        sbSong.setMax(mediaPlayer.getDuration());
    }
    private void KhoiTaoMediaPlayer() {
        mediaPlayer = MediaPlayer.create(MediaPlayerActivity.this, arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());
    }
    private void AddSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song ("Chưa bao giờ", R.raw.chuabaogio));
        arraySong.add(new Song ("Buồn hay vui", R.raw.buonhayvui));
        arraySong.add(new Song ("Lạc trôi",     R.raw.lactroi));
        arraySong.add(new Song ("Tại vì sao",   R.raw.taivisao));
    }

    private void AnhXa(){
        txtTimeSong =  (TextView)       findViewById(R.id.tvTimeSong);
        txtTimeTotal = (TextView)       findViewById(R.id.tvTimeTotal);
        txtTitle =     (TextView)       findViewById(R.id.tvTitle);
        sbSong =       (SeekBar)        findViewById(R.id.seekBar);
        btnPrev =      (ImageButton)    findViewById(R.id.btn_Prev);
        btnPlay =      (ImageButton)    findViewById(R.id.btn_Play);
        btnRestart =   (ImageButton)    findViewById(R.id.btn_Pause);
        btnForward =   (ImageButton)    findViewById(R.id.btn_Forward);
        imgDisc =      (ImageView)      findViewById(R.id.imgDisc);
    }

}