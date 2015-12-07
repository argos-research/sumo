function roadID = getRoadID(personID)
%getRoadID Returns the edge the person was at in last step. 
%   roadID = getRoadID(PERSONID) Returns the id of the edge the named person 
%   was at within the last step.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
roadID = traci.person.getUniversal(constants.VAR_ROAD_ID, personID);