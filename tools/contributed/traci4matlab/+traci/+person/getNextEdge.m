function nextEdge = getNextEdge(personID)
%getNextEdge Returns next edge on the persons route.
%   nextEdge = getNextEdge(PERSONID) returns the next edge on the persons 
%   route (including crossing and walkingareas). If there is no further
%   edge or the person is in another stage, returns the empty string.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getNextEdge.m 25 2015-06-25 22:38:10Z afacostag $

import traci.constants
nextEdge = traci.person.getUniversal(constants.VAR_NEXT_EDGE, personID);