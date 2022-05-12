<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="currentPage" required="true"%>
<%@ attribute name="totalPages" required="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="text-center">
	<ul class="pagination pagination-sm">
		<li class="${currentPage == 1 ? 'disabled' : ''}"><a
			href="<c:out value="${currentPage-1 < 1 ? '#' : '?command=trips_page&page='+=currentPage-1}"/>">
				<span class="glyphicon glyphicon-chevron-left"></span>
		</a></li>
		<c:choose>

			<c:when test="${totalPages > 4}">
				<li class="${currentPage == 1 ? 'active' : ''}"><a
					href="?command=trips_page&page=1">1</a></li>
				<c:choose>

					<c:when test="${currentPage > 2 && currentPage < totalPages-1}">

						<c:if test="${currentPage > totalPages/2}">
							<li class="disabled"><a href=""><span
									class="glyphicon glyphicon-option-horizontal"></span></a></li>
						</c:if>

						<c:forEach begin="${currentPage-1}" end="${currentPage+1}"
							varStatus="page">

							<li class="${currentPage == page.index ? 'active' : ''}"><a
								href="?command=trips_page&page=<c:out value="${page.index}"/>">
									<c:out value="${page.index}" />
							</a></li>

						</c:forEach>

						<c:if test="${currentPage <= totalPages/2}">
							<li class="disabled"><a href=""><span
									class="glyphicon glyphicon-option-horizontal"></span></a></li>
						</c:if>

					</c:when>

					<c:otherwise>

						<c:choose>

							<c:when test="${currentPage <= 2}">

								<li class="${currentPage == 2 ? 'active' : ''}"><a
									href="?command=trips_page&page=2">2</a></li>
								<li class=""><a href="?command=trips_page&page=3">3</a></li>
								<li class="disabled"><a href="#"><span
										class="glyphicon glyphicon-option-horizontal"></span></a></li>

							</c:when>

							<c:otherwise>

								<li class="disabled"><a href="#"> <span
										class="glyphicon glyphicon-option-horizontal"></span>
								</a></li>

								<li class=""><a
									href="?command=trips_page&page=<c:out value="${totalPages-2}"/>">
										<c:out value="${totalPages-2}" />
								</a></li>

								<li class="${currentPage == totalPages-1 ? 'active' : ''}">
									<a
									href="?command=trips_page&page=<c:out value="${totalPages-1}"/>">
										<c:out value="${totalPages-1}" />
								</a>
								</li>

							</c:otherwise>

						</c:choose>

					</c:otherwise>

				</c:choose>

				<li class="${currentPage == totalPages ? 'active' : ''}"><a
					href="?command=trips_page&page=<c:out value="${totalPages}"/>">
						<c:out value="${totalPages}" />
				</a></li>

			</c:when>

			<c:otherwise>

				<c:forEach begin="1" end="${totalPages}" varStatus="page">

					<li class="${currentPage == page.index ? 'active' : ''}"><a
						href="?command=trips_page&page=${page.index}"> <c:out
								value="${page.index}" />
					</a></li>

				</c:forEach>

			</c:otherwise>

		</c:choose>

		<li class="${currentPage == totalPages ? 'disabled' : ''}"><a
			href="<c:out value="${currentPage+1 > totalPages ? '#' : '?command=trips_page&page='+=currentPage+1}"/>">
				<span class="glyphicon glyphicon-chevron-right"></span>
		</a></li>

	</ul>
</div>