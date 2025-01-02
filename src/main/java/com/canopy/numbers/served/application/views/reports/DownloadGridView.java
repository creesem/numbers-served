package com.canopy.numbers.served.application.views.reports;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;

public abstract class DownloadGridView<T> extends VerticalLayout implements RouterLayout {

	private static final long serialVersionUID = -5321292302408689804L;

	protected StreamRegistration registration;

	/**
	 * Generate an Excel file in byte[] format for the given list of items.
	 */
	protected abstract byte[] generateExcel(List<T> items);

	/**
	 * Initiates the download service for the selected items.
	 */
	protected void callDownloadService(List<T> selectedItems) {
		if (selectedItems == null || selectedItems.isEmpty()) {
			Notification.show("No items selected for download", 3000, Notification.Position.MIDDLE);
			return;
		}

		byte[] excelBytes = generateExcel(selectedItems);
		if (excelBytes != null) {
			StreamResource resource = createStreamResource(excelBytes);
			initiateDownload(resource);
		} else {
			Notification.show("Error generating Excel file", 3000, Notification.Position.MIDDLE);
		}
	}

	/**
	 * Creates a StreamResource for the Excel file.
	 */
	private StreamResource createStreamResource(byte[] excelBytes) {
		return new StreamResource("items.xlsx", () -> new ByteArrayInputStream(excelBytes));
	}

	/**
	 * Initiates the download process for the given StreamResource.
	 */
	private void initiateDownload(StreamResource resource) {
		getUI().ifPresent(ui -> {
			registration = ui.getSession().getResourceRegistry().registerResource(resource);
			String resourceUrl = registration.getResourceUri().toString();
			ui.getPage().executeJs(
					"var link = document.createElement('a');" + "link.href = $0;"
							+ "link.download = 'cares_report.csv';" + "document.body.appendChild(link);"
							+ "link.click();" + "document.body.removeChild(link);"
							+ "setTimeout(function() { $1.$server.unregisterResource(); }, 500);",
					resourceUrl, getElement());
		});
	}

	/**
	 * Retrieves the unique identifier for the given item.
	 */
	protected abstract String getItemId(T item);
}
