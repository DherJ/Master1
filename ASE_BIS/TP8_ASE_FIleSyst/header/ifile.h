#ifndef i_file_h
#define i_file_h

#define READ_EOF	-1

struct file_desc_s {
	int inumber;
	int offset;
	int size;
	char buffer[HDA_SECTORSIZE];
	int dirty;
};


extern uint create_ifile(enum file_type_e type);
extern int delete_ifile(uint inumber);
extern int open_ifile(file_desc_s *fd, uint inumber);
extern void close_ifile(file_desc_s *fd);
extern void flush_ifile(file_desc_s *fd);
extern void seek_ifile(file_desc_s *fd, int r_offset);  /* relatif */
extern void seek2_ifile(file_desc_s *fd int a_offset);  /* absolu */
extern int readc_ifile(file_desc_s *fd);
extern int writec_ifile(file_desc_s *fd, char c);
extern int read_ifile(file_desc_s *fd, void *buf, uint nbyte);
extern int write_ifile(file_desc_s *fd, const void *buf, uint nbyte);


#endif /* i_file_h */