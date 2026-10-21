package br.com.suryadental.application.service;

import br.com.suryadental.application.dto.response.ImportResponse;
import br.com.suryadental.application.model.Client;
import br.com.suryadental.application.model.Ticket;
import br.com.suryadental.application.repository.ClientRepository;
import br.com.suryadental.application.repository.TicketRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class ImportService {

    private final ClientRepository clientRepository;
    private final TicketRepository ticketRepository;

    public ImportService(
            ClientRepository clientRepository,
            TicketRepository ticketRepository
    ) {
        this.clientRepository = clientRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public ImportResponse importFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return new ImportResponse(
                    false,
                    "File is empty",
                    0,
                    0,
                    0,
                    0
            );
        }

        String filename = file.getOriginalFilename();

        if (filename == null || !filename.toLowerCase().endsWith(".xlsm")) {
            return new ImportResponse(
                    false,
                    "Only .xlsm files are supported",
                    0,
                    0,
                    0,
                    0
            );
        }

        int rowsProcessed = 0;
        int clientsCreated = 0;
        int ticketsCreated = 0;
        int rowsWithErrors = 0;

        List<String> errors = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            if (sheet.getPhysicalNumberOfRows() <= 1) {
                return new ImportResponse(
                        false,
                        "The spreadsheet does not contain data",
                        0,
                        0,
                        0,
                        0
                );
            }

            DataFormatter formatter = new DataFormatter();

            Row headerRow = sheet.getRow(0);

            if (headerRow == null) {
                return new ImportResponse(
                        false,
                        "Spreadsheet header was not found",
                        0,
                        0,
                        0,
                        0
                );
            }

            Map<String, Integer> columns = readColumns(headerRow, formatter);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {

                Row row = sheet.getRow(rowIndex);

                if (row == null || isEmptyRow(row, formatter)) {
                    continue;
                }

                try {
                    String clientName = getCell(
                            row,
                            columns,
                            "Cliente (Completo)",
                            formatter
                    );

                    String clientCode = getCell(
                            row,
                            columns,
                            "Código do Cliente",
                            formatter
                    );

                    String ticketNumber = getCell(
                            row,
                            columns,
                            "Número",
                            formatter
                    );

                    /*
                     * Skip duplicated tickets when the external ticket number
                     * already exists in the database.
                     */
                    if (!ticketNumber.isBlank()
                            && ticketRepository.findByTicketNumber(ticketNumber).isPresent()) {

                        continue;
                    }

                    Client client = null;

                    if (!clientName.isBlank() || !clientCode.isBlank()) {

                        Optional<Client> existingClient = Optional.empty();

                        if (!clientCode.isBlank()) {
                            existingClient = clientRepository.findByClientCode(clientCode);
                        }

                        if (existingClient.isEmpty() && !clientName.isBlank()) {
                            existingClient = clientRepository.findByName(clientName);
                        }

                        if (existingClient.isPresent()) {
                            client = existingClient.get();
                        } else {
                            client = new Client();
                            client.setName(clientName);
                            client.setClientCode(
                                    clientCode.isBlank() ? null : clientCode
                            );

                            client = clientRepository.save(client);
                            clientsCreated++;
                        }
                    }

                    Ticket ticket = new Ticket();

                    ticket.setOpenedAt(getDate(
                            row, columns, "Aberto em", formatter
                    ));

                    ticket.setCallAnalysis(getCell(
                            row, columns, "Análise do Chamado", formatter
                    ));

                    ticket.setSubject(getCell(
                            row, columns, "Assunto", formatter
                    ));

                    ticket.setCategory(getCell(
                            row, columns, "Categoria", formatter
                    ));

                    ticket.setClient(client);

                    ticket.setCreatedBy(getCell(
                            row, columns, "Criado por", formatter
                    ));

                    ticket.setSalesStructure(getCell(
                            row, columns, "Estrutura de Venda", formatter
                    ));

                    ticket.setAgentFailure(getCell(
                            row, columns, "Falha do Agente", formatter
                    ));

                    ticket.setClientFailure(getCell(
                            row, columns, "Falha do Cliente", formatter
                    ));

                    ticket.setClosedAt(getDate(
                            row, columns, "Fechado em", formatter
                    ));

                    ticket.setJustification(getCell(
                            row, columns, "Justificativa", formatter
                    ));

                    ticket.setReason(getCell(
                            row, columns, "Motivo", formatter
                    ));

                    ticket.setImproperReason(getCell(
                            row, columns, "Motivo da Improcedência", formatter
                    ));

                    ticket.setNfe(getCell(
                            row, columns, "N° NF-e", formatter
                    ));

                    ticket.setCollaboratorName(getCell(
                            row, columns, "Nome do Colaborador", formatter
                    ));

                    ticket.setExchangeReturnReason(getCell(
                            row, columns, "Motivo \\ Trocas e Dev.", formatter
                    ));

                    ticket.setSellerName(getCell(
                            row, columns, "Nome do Vendedor", formatter
                    ));

                    ticket.setTicketNumber(ticketNumber);

                    ticket.setFirstResponsible(getCell(
                            row, columns, "Primeiro responsável", formatter
                    ));

                    ticket.setResolvedAt(getDate(
                            row, columns, "Resolvido em", formatter
                    ));

                    ticket.setResponsible(getCell(
                            row, columns, "Responsável", formatter
                    ));

                    ticket.setActionCount(getInteger(
                            row, columns, "Quantidade de ações", formatter
                    ));

                    ticket.setFailureResponsible(getCell(
                            row, columns, "Responsável pela Falha", formatter
                    ));

                    ticket.setStatus(getCell(
                            row, columns, "Status", formatter
                    ));

                    ticket.setService(getCell(
                            row, columns, "Serviço (Completo)", formatter
                    ));

                    ticketRepository.save(ticket);

                    ticketsCreated++;
                    rowsProcessed++;

                } catch (Exception exception) {

                    rowsWithErrors++;

                    errors.add(
                            "Row " + (rowIndex + 1) + ": "
                                    + exception.getMessage()
                    );
                }
            }

            String message;

            if (rowsWithErrors == 0) {
                message = "Import completed successfully";
            } else {
                message = "Import completed with "
                        + rowsWithErrors
                        + " row errors";
            }

            return new ImportResponse(
                    true,
                    message,
                    rowsProcessed,
                    clientsCreated,
                    ticketsCreated,
                    rowsWithErrors
            );

        } catch (Exception exception) {

            return new ImportResponse(
                    false,
                    "Failed to read spreadsheet: " + exception.getMessage(),
                    rowsProcessed,
                    clientsCreated,
                    ticketsCreated,
                    rowsWithErrors
            );
        }
    }

    private Map<String, Integer> readColumns(
            Row headerRow,
            DataFormatter formatter
    ) {
        Map<String, Integer> columns = new HashMap<>();

        for (Cell cell : headerRow) {
            String header = formatter.formatCellValue(cell);

            if (header != null && !header.isBlank()) {
                columns.put(normalize(header), cell.getColumnIndex());
            }
        }

        return columns;
    }

    private String getCell(
            Row row,
            Map<String, Integer> columns,
            String columnName,
            DataFormatter formatter
    ) {
        Integer columnIndex = columns.get(normalize(columnName));

        if (columnIndex == null) {
            return "";
        }

        Cell cell = row.getCell(columnIndex);

        if (cell == null) {
            return "";
        }

        return formatter.formatCellValue(cell).trim();
    }

    private LocalDateTime getDate(
            Row row,
            Map<String, Integer> columns,
            String columnName,
            DataFormatter formatter
    ) {
        Integer columnIndex = columns.get(normalize(columnName));

        if (columnIndex == null) {
            return null;
        }

        Cell cell = row.getCell(columnIndex);

        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }

        if (cell.getCellType() == CellType.NUMERIC
                && DateUtil.isCellDateFormatted(cell)) {

            return cell.getDateCellValue()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        }

        String value = formatter.formatCellValue(cell).trim();

        if (value.isBlank()) {
            return null;
        }

        List<String> patterns = List.of(
                "dd/MM/yyyy HH:mm:ss",
                "dd/MM/yyyy HH:mm",
                "dd/MM/yyyy"
        );

        for (String pattern : patterns) {
            try {
                if (pattern.equals("dd/MM/yyyy")) {
                    return java.time.LocalDate
                            .parse(value, java.time.format.DateTimeFormatter.ofPattern(pattern))
                            .atStartOfDay();
                }

                return LocalDateTime.parse(
                        value,
                        java.time.format.DateTimeFormatter.ofPattern(pattern)
                );

            } catch (Exception ignored) {
            }
        }

        throw new IllegalArgumentException(
                "Invalid date: " + value
        );
    }

    private Integer getInteger(
            Row row,
            Map<String, Integer> columns,
            String columnName,
            DataFormatter formatter
    ) {
        String value = getCell(
                row,
                columns,
                columnName,
                formatter
        );

        if (value.isBlank()) {
            return null;
        }

        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException exception) {
            try {
                return (int) Double.parseDouble(value.replace(",", "."));
            } catch (NumberFormatException ignored) {
                throw new IllegalArgumentException(
                        "Invalid integer: " + value
                );
            }
        }
    }

    private boolean isEmptyRow(
            Row row,
            DataFormatter formatter
    ) {
        for (Cell cell : row) {
            if (!formatter.formatCellValue(cell).trim().isBlank()) {
                return false;
            }
        }

        return true;
    }

    private String normalize(String value) {
        return value
                .trim()
                .toLowerCase(Locale.ROOT)
                .replaceAll("\\s+", " ");
    }
}