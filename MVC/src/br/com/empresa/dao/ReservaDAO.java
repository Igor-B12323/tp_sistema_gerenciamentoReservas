package br.com.empresa.dao;

import br.com.empresa.model.Reserva;
import br.com.empresa.model.ReservaDTO;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public List<Reserva> listarTodas() throws SQLException {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM pj6.Reserva";

        try (Connection conexao = ConexaoBD.getConnection();
                Statement comandoSQL = conexao.createStatement();
                ResultSet registros = comandoSQL.executeQuery(sql)) {

            while (registros.next()) {

                Date dtIni = registros.getDate("dataInicial");
                Date dtFim = registros.getDate("dataFinal");
                Time hrIni = registros.getTime("horaInicial");
                Time hrFim = registros.getTime("horaFinal");

                var reservaLido =
                        new Reserva(
                                registros.getInt("id"),
                                (dtIni != null) ? dtIni.toLocalDate() : null,
                                (dtFim != null) ? dtFim.toLocalDate() : null,
                                (hrIni != null) ? hrIni.toLocalTime() : null,
                                (hrFim != null) ? hrFim.toLocalTime() : null,
                                registros.getInt("id_statusreserva"),
                                registros.getInt("id_usuario"),
                                registros.getInt("id_email"));
                lista.add(reservaLido);
            }
        }
        return lista;
    }

    public List<ReservaDTO> listarParaTabela() throws SQLException {
        List<ReservaDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM pj6.vw_dadosReservaRecurso";

        try (Connection conexao = ConexaoBD.getConnection();
                Statement comandoSQL = conexao.createStatement();
                ResultSet registros = comandoSQL.executeQuery(sql)) {

            while (registros.next()) {

                Date dtIni = registros.getDate("dataInicial");
                Date dtFim = registros.getDate("dataFinal");
                Time hrIni = registros.getTime("horaInicial");
                Time hrFim = registros.getTime("horaFinal");

                ReservaDTO reservaLida = new ReservaDTO();
                
                reservaLida.setId(registros.getInt("idReserva"));
                reservaLida.setNomeRecurso(registros.getString("nomeRecurso"));
                reservaLida.setCapacidade(registros.getInt("capacidade"));
                reservaLida.setLocalizacao(registros.getString("localizacao"));
                reservaLida.setDataInicial((dtIni != null) ? dtIni.toLocalDate() : null);
                reservaLida.setDataFinal((dtFim != null) ? dtFim.toLocalDate() : null);
                reservaLida.setHoraInicial((hrIni != null) ? hrIni.toLocalTime() : null);
                reservaLida.setHoraFinal((hrFim != null) ? hrFim.toLocalTime() : null);
                reservaLida.setNomeStatusReserva(registros.getString("statusReserva"));
                reservaLida.setNomeUsuario(registros.getString("nomeUsuario"));
                reservaLida.setEmail(registros.getString("email"));
                        
                lista.add(reservaLida);
            }
        }
        return lista;
    }

    public Reserva existe(int idProcurado) throws SQLException {
        String sql = "SELECT * FROM pj6.reserva WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConnection();
                PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

            comandoSQL.setInt(1, idProcurado);

            try (ResultSet registros = comandoSQL.executeQuery()) {
                if (registros.next()) {

                    Date dtIni = registros.getDate("dataInicial");
                    Date dtFim = registros.getDate("dataFinal");
                    Time hrIni = registros.getTime("horaInicial");
                    Time hrFim = registros.getTime("horaFinal");

                    return new Reserva(
                            registros.getInt("id"),
                            (dtIni != null) ? dtIni.toLocalDate() : null,
                            (dtFim != null) ? dtFim.toLocalDate() : null,
                            (hrIni != null) ? hrIni.toLocalTime() : null,
                            (hrFim != null) ? hrFim.toLocalTime() : null,
                            registros.getInt("id_recurso"),
                            registros.getInt("id_usuario"),
                            registros.getInt("id_statusreserva"));
                }
            }
        }
        return null;
    }

    public Reserva existe(
            LocalDate dtIniProc,
            LocalDate dtFimProc,
            LocalTime hrIniProc,
            LocalTime hrFimProc,
            int idRecurso)
            throws SQLException {
        String sql  = "SELECT * FROM pj6.reserva "
                    + "WHERE dataInicial = ? "
                    + "  AND dataFinal = ? "
                    + "  AND CAST(horaInicial AS TIME) = CAST(? AS TIME) "
                    + "  AND CAST(horaFinal AS TIME) = CAST(? AS TIME) "
                    + "  AND id_recurso = ?";

        try (Connection conexao = ConexaoBD.getConnection();
                PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

            comandoSQL.setDate(1, dtIniProc != null ? java.sql.Date.valueOf(dtIniProc) : null);
            comandoSQL.setDate(2, dtFimProc != null ? java.sql.Date.valueOf(dtFimProc) : null);
            comandoSQL.setTime(3, hrIniProc != null ? java.sql.Time.valueOf(hrIniProc) : null);
            comandoSQL.setTime(4, hrFimProc != null ? java.sql.Time.valueOf(hrFimProc) : null);
            comandoSQL.setInt(5, idRecurso);

            try (ResultSet registros = comandoSQL.executeQuery()) {
                if (registros.next()) {

                    Date dtIni = registros.getDate("dataInicial");
                    Date dtFim = registros.getDate("dataFinal");
                    Time hrIni = registros.getTime("horaInicial");
                    Time hrFim = registros.getTime("horaFinal");

                    return new Reserva(
                            registros.getInt("id"),
                            (dtIni != null) ? dtIni.toLocalDate() : null,
                            (dtFim != null) ? dtFim.toLocalDate() : null,
                            (hrIni != null) ? hrIni.toLocalTime() : null,
                            (hrFim != null) ? hrFim.toLocalTime() : null,
                            registros.getInt("id_recurso"),
                            registros.getInt("id_usuario"),
                            registros.getInt("id_statusreserva"));
                }
            }
        }
        return null;
    }

    public List<ReservaDTO> consultar(
            LocalDate data,
            LocalTime hora,
            Integer idRecurso,
            String nomeRecurso,
            String nomeUsuario,
            String nomeStatus)
            throws SQLException {

        List<ReservaDTO> lista = new ArrayList<>();
        String sql  = "SELECT res.id as codReserva, rec.id as codRecurso, rec.nome as nomeRecurso,"
                    + " rec.capacidade, rec.localizacao, u.email, res.dataInicial, res.dataFinal,"
                    + " res.horaInicial, res.horaFinal, u.nome as nomeUsuario, s.nome as nomeStatus"
                    + " FROM pj6.reserva as res"
                    + " INNER JOIN pj6.recurso as rec ON rec.id = res.id_recurso"
                    + " INNER JOIN pj6.usuario as u ON u.id = res.id_usuario"
                    + " INNER JOIN pj6.statusReserva as s ON s.id = res.id_statusreserva"
                    + " WHERE 1=1";

        if (data != null) {
            sql += (" AND ? BETWEEN res.dataInicial AND res.dataFinal");
        }
        if (hora != null) {
            sql += (" AND CAST(? AS TIME) BETWEEN CAST(res.horaInicial AS TIME) AND CAST(res.horaFinal AS TIME)");
        }
        if (idRecurso != null && idRecurso > 0) {
            sql += (" AND res.id_recurso = ?");
        }
        if (nomeUsuario != null && !nomeUsuario.trim().isEmpty()) {
            sql += (" AND u.nome LIKE ?");
        }
        if (nomeStatus != null && !nomeStatus.trim().isEmpty()) {
            sql += (" AND s.nome LIKE ?");
        }
        if (nomeRecurso != null && !nomeRecurso.trim().isEmpty()) {
            sql += (" AND rec.nome LIKE ?");
        }

        try (Connection conexao = ConexaoBD.getConnection();
                PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

            int index = 1;
            if (data != null){ comandoSQL.setDate(index++, java.sql.Date.valueOf(data));}
            if (hora != null){ comandoSQL.setTime(index++, java.sql.Time.valueOf(hora));}
            if (idRecurso != null){ comandoSQL.setInt(index++, idRecurso);}
            if (nomeUsuario != null){ comandoSQL.setString(index++, "%" + nomeUsuario + "%");}
            if (nomeStatus != null){ comandoSQL.setString(index++, "%" + nomeStatus + "%");}
            if (nomeRecurso != null && !nomeRecurso.trim().isEmpty()){
                comandoSQL.setString(index++, "%" + nomeRecurso + "%");}

            try (ResultSet registros = comandoSQL.executeQuery()) {
                while (registros.next()) {

                    Date dtIni = registros.getDate("dataInicial");
                    Date dtFim = registros.getDate("dataFinal");
                    Time hrIni = registros.getTime("horaInicial");
                    Time hrFim = registros.getTime("horaFinal");

                    ReservaDTO dto = new ReservaDTO();
                    dto.setId(registros.getInt("codReserva"));
                    dto.setIdRecurso(registros.getInt("codRecurso"));
                    dto.setDataInicial((dtIni != null) ? dtIni.toLocalDate() : null);
                    dto.setDataFinal((dtFim != null) ? dtFim.toLocalDate() : null);
                    dto.setHoraInicial((hrIni != null) ? hrIni.toLocalTime() : null);
                    dto.setHoraFinal((hrFim != null) ? hrFim.toLocalTime() : null);
                    dto.setNomeRecurso(registros.getString("nomeRecurso"));
                    dto.setNomeUsuario(registros.getString("nomeusuario"));
                    dto.setNomeStatusReserva(registros.getString("nomestatus"));

                    lista.add(dto);
                }
            }
        }
        return lista;
    }

    public void inserir(Reserva reserva) throws SQLException {
        String sql = "{call pj6.proc_inserirReserva(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conexao = ConexaoBD.getConnection();
                CallableStatement cs = conexao.prepareCall(sql)) {

            if (reserva.getDataInicial() != null) {
                cs.setDate(1, Date.valueOf(reserva.getDataInicial()));
            } else {
                cs.setNull(1, Types.DATE);
            }

            if (reserva.getDataFinal() != null) {
                cs.setDate(2, Date.valueOf(reserva.getDataFinal()));
            } else {
                cs.setNull(2, Types.DATE);
            }

            if (reserva.getHoraInicial() != null) {
                cs.setTime(3, Time.valueOf(reserva.getHoraInicial()));
            } else {
                cs.setNull(3, Types.TIME);
            }

            if (reserva.getHoraFinal() != null) {
                cs.setTime(4, Time.valueOf(reserva.getHoraFinal()));
            } else {
                cs.setNull(4, Types.TIME);
            }

            cs.setInt(5, reserva.getIdRecurso());
            cs.setInt(6, reserva.getIdUsuario());
            cs.setInt(7, reserva.getIdStatusReserva());

            cs.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "{call pj6.proc_excluirReserva(?)}";

        try (Connection conexao = ConexaoBD.getConnection();
                CallableStatement cs = conexao.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.executeUpdate();
        }
    }

    public void atualizar(Reserva reserva) throws SQLException {
        String sql = "{call pj6.proc_atualizarReserva(?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conexao = ConexaoBD.getConnection();
                CallableStatement cs = conexao.prepareCall(sql)) {

            cs.setInt(1, reserva.getId());

            if (reserva.getDataInicial() != null) {
                cs.setDate(2, Date.valueOf(reserva.getDataInicial()));
            } else {
                cs.setNull(2, Types.DATE);
            }

            if (reserva.getDataFinal() != null) {
                cs.setDate(3, Date.valueOf(reserva.getDataFinal()));
            } else {
                cs.setNull(3, Types.DATE);
            }

            if (reserva.getHoraInicial() != null) {
                cs.setTime(4, Time.valueOf(reserva.getHoraInicial()));
            } else {
                cs.setNull(4, Types.TIME);
            }

            if (reserva.getHoraFinal() != null) {
                cs.setTime(5, Time.valueOf(reserva.getHoraFinal()));
            } else {
                cs.setNull(5, Types.TIME);
            }

            cs.setInt(6, reserva.getIdRecurso());
            cs.setInt(7, reserva.getIdUsuario());
            cs.setInt(8, reserva.getIdStatusReserva());

            cs.executeUpdate();
        }
    }
}
