package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import DTO.NoticeDTO;

public class NoticeDAO {
    private DBConnectionMgr pool;
    
    public NoticeDAO() {
        try {
            pool = DBConnectionMgr.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private final SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");
    
    // 공지사항 목록 가져오기
    public List<NoticeDTO> getNoticeList(int start, int end) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<NoticeDTO> noticeList = new ArrayList<>();
        
        try {
            con = pool.getConnection();
            
            // 중요 공지는 먼저, 그 다음에 최신순으로 정렬
            String sql = "SELECT * FROM notice ORDER BY noti_isPinned DESC, created_at DESC LIMIT ?, ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, start);
            pstmt.setInt(2, end);
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                NoticeDTO notice = new NoticeDTO();
                notice.setNoti_id(rs.getInt("noti_id"));
                notice.setAdmin_id(rs.getString("admin_id"));
                notice.setNoti_title(rs.getString("noti_title"));
                notice.setContent(rs.getString("noti_content"));
                notice.setCreated_at(rs.getString("created_at"));
                notice.setNoti_views(rs.getInt("noti_views"));
                notice.setNoti_isPinned(rs.getString("noti_isPinned"));
                
                noticeList.add(notice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        
        return noticeList;
    }
    
    // 전체 공지사항 개수 가져오기
    public int getTotalNoticeCount() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        
        try {
            con = pool.getConnection();
            String sql = "SELECT COUNT(*) FROM notice";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        
        return count;
    }
    
    // 공지사항 상세 보기
    public NoticeDTO getNotice(int notiId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        NoticeDTO notice = null;
        
        try {
            con = pool.getConnection();
            
            // 조회수 증가
            String updateSql = "UPDATE notice SET noti_views = noti_views + 1 WHERE noti_id = ?";
            pstmt = con.prepareStatement(updateSql);
            pstmt.setInt(1, notiId);
            pstmt.executeUpdate();
            
            // 공지사항 가져오기
            String sql = "SELECT * FROM notice WHERE noti_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, notiId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                notice = new NoticeDTO();
                notice.setNoti_id(rs.getInt("noti_id"));
                notice.setAdmin_id(rs.getString("admin_id"));
                notice.setNoti_title(rs.getString("noti_title"));
                notice.setContent(rs.getString("noti_content"));
                notice.setCreated_at(rs.getString("created_at"));
                notice.setNoti_views(rs.getInt("noti_views"));
                notice.setNoti_isPinned(rs.getString("noti_isPinned"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        
        return notice;
    }
    
    // 공지사항 추가
    public boolean insertNotice(NoticeDTO notice) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            con = pool.getConnection();
            String sql = "INSERT INTO notice (admin_id, noti_title, noti_content, created_at, noti_isPinned) VALUES (?, ?, ?, NOW(), ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, notice.getAdmin_id());
            pstmt.setString(2, notice.getNoti_title());
            pstmt.setString(3, notice.getContent());
            pstmt.setString(4, notice.getNoti_isPinned());
            
            int count = pstmt.executeUpdate();
            if (count > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        
        return result;
    }
    
    // 공지사항 수정
    public boolean updateNotice(NoticeDTO notice) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            con = pool.getConnection();
            String sql = "UPDATE notice SET noti_title = ?, noti_content = ?, noti_isPinned = ? WHERE noti_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, notice.getNoti_title());
            pstmt.setString(2, notice.getContent());
            pstmt.setString(3, notice.getNoti_isPinned());
            pstmt.setInt(4, notice.getNoti_id());
            
            int count = pstmt.executeUpdate();
            if (count > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        
        return result;
    }
    
    // 공지사항 삭제
    public boolean deleteNotice(int notiId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            con = pool.getConnection();
            String sql = "DELETE FROM notice WHERE noti_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, notiId);
            
            int count = pstmt.executeUpdate();
            if (count > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        
        return result;
    }
    
    // 중요 공지 설정/해제
    public boolean togglePinStatus(int notiId, String isPinned) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            con = pool.getConnection();
            String sql = "UPDATE notice SET noti_isPinned = ? WHERE noti_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, isPinned);
            pstmt.setInt(2, notiId);
            
            int count = pstmt.executeUpdate();
            if (count > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt);
        }
        
        return result;
    }
    
    // 검색
    public List<NoticeDTO> searchNotices(String searchType, String keyword, int start, int end) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<NoticeDTO> noticeList = new ArrayList<>();
        
        try {
            con = pool.getConnection();
            
            String sql = "SELECT * FROM notice WHERE ";
            if (searchType.equals("title")) {
                sql += "noti_title LIKE ?";
            } else if (searchType.equals("content")) {
                sql += "noti_content LIKE ?";
            } else {
                sql += "(noti_title LIKE ? OR noti_content LIKE ?)";
            }
            sql += " ORDER BY noti_isPinned DESC, created_at DESC LIMIT ?, ?";
            
            pstmt = con.prepareStatement(sql);
            
            if (searchType.equals("title") || searchType.equals("content")) {
                pstmt.setString(1, "%" + keyword + "%");
                pstmt.setInt(2, start);
                pstmt.setInt(3, end);
            } else {
                pstmt.setString(1, "%" + keyword + "%");
                pstmt.setString(2, "%" + keyword + "%");
                pstmt.setInt(3, start);
                pstmt.setInt(4, end);
            }
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                NoticeDTO notice = new NoticeDTO();
                notice.setNoti_id(rs.getInt("noti_id"));
                notice.setAdmin_id(rs.getString("admin_id"));
                notice.setNoti_title(rs.getString("noti_title"));
                notice.setContent(rs.getString("noti_content"));
                notice.setCreated_at(rs.getString("created_at"));
                notice.setNoti_views(rs.getInt("noti_views"));
                notice.setNoti_isPinned(rs.getString("noti_isPinned"));
                
                noticeList.add(notice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        
        return noticeList;
    }
    
    // 검색 결과 개수
    public int getSearchResultCount(String searchType, String keyword) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        
        try {
            con = pool.getConnection();
            
            String sql = "SELECT COUNT(*) FROM notice WHERE ";
            if (searchType.equals("title")) {
                sql += "noti_title LIKE ?";
            } else if (searchType.equals("content")) {
                sql += "noti_content LIKE ?";
            } else {
                sql += "(noti_title LIKE ? OR noti_content LIKE ?)";
            }
            
            pstmt = con.prepareStatement(sql);
            
            if (searchType.equals("title") || searchType.equals("content")) {
                pstmt.setString(1, "%" + keyword + "%");
            } else {
                pstmt.setString(1, "%" + keyword + "%");
                pstmt.setString(2, "%" + keyword + "%");
            }
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        
        return count;
    }
    
    //중요 공지사항 출력
    public Vector<NoticeDTO> showImpNotice(){
    	Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<NoticeDTO> nlist = new Vector<NoticeDTO>();
		try {
			con = pool.getConnection();
			sql = "select * from notice where noti_isPinned = 'Y' order by created_at desc";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				nlist.add(new NoticeDTO(rs.getInt(1), rs.getString(2), 
																rs.getString(3), rs.getString(4), 
																SDF_DATE.format(rs.getDate(5)), rs.getInt(6), rs.getString(7)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return nlist;
    }
    
    //일반 공지사항 출력
    public Vector<NoticeDTO> showNotImpNotice(){
    	Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<NoticeDTO> nlist = new Vector<NoticeDTO>();
		try {
			con = pool.getConnection();
			sql = "select * from notice where noti_isPinned = 'N' order by created_at desc";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				nlist.add(new NoticeDTO(rs.getInt(1), rs.getString(2), 
																rs.getString(3), rs.getString(4), 
																SDF_DATE.format(rs.getDate(5)), rs.getInt(6), rs.getString(7)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return nlist;
    }
    
    //중요공지사항인지 확인
    public boolean isPinned(int id) {
    	Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "select noti_isPinned from notice where noti_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if("Y".equals(rs.getString(1)))
					flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return flag;
    }
    
    // 공지사항 상세 보기(관리자용)
    public NoticeDTO getNoticeForAdmin(int notiId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        NoticeDTO notice = null;
        
        try {
            con = pool.getConnection();
            // 공지사항 가져오기
            String sql = "SELECT * FROM notice WHERE noti_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, notiId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                notice = new NoticeDTO();
                notice.setNoti_id(rs.getInt("noti_id"));
                notice.setAdmin_id(rs.getString("admin_id"));
                notice.setNoti_title(rs.getString("noti_title"));
                notice.setContent(rs.getString("noti_content"));
                notice.setCreated_at(rs.getString("created_at"));
                notice.setNoti_views(rs.getInt("noti_views"));
                notice.setNoti_isPinned(rs.getString("noti_isPinned"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        
        return notice;
    }
} 