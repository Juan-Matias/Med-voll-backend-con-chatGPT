package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.dto.DatosAgendarConsulta;
import med.voll.api.domain.consulta.dto.DatosCancelamientoConsulta;
import med.voll.api.domain.consulta.dto.DatosDetalleConsulta;
import med.voll.api.domain.consulta.dto.DatosReporteConsultaMensual;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/consultas")

@SuppressWarnings("all")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;

    private ConsultaRepository consultaRepository; // ✅ FALTABA ESTO


    @GetMapping
    public ResponseEntity<Page<DatosDetalleConsulta>> listar(@PageableDefault(size = 10, sort = {"fecha"}) Pageable paginacion) {
        var response = service.consultar(paginacion);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos) throws ValidacionDeIntegridad {
        var response = service.agendar(datos);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DatosCancelamientoConsulta datos) {
        service.cancelar(datos);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorio-mensual/{mes}")
    public ResponseEntity<List<DatosReporteConsultaMensual>> generarRelatorioConsultaMensual(@PathVariable YearMonth mes) {
        LocalDateTime inicio = mes.atDay(1).atStartOfDay();
        LocalDateTime fin = mes.atEndOfMonth().atTime(23, 59, 59);

        var relatorio = consultaRepository.relatorioMensual(inicio, fin);
        return ResponseEntity.ok(relatorio);
    }

}
