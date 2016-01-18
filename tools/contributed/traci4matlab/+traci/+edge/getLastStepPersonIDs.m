function personIDs = getLastStepPersonIDs(edgeID)
%getLastStepPersonIDs Returns the ids of the persons in the last time step.
%   personIDs = getLastStepPersonIDs(EDGEID) Returns the ids of the persons
%   on the given edge during the last time step.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLastStepPersonIDs.m 25 2015-06-25 22:38:10Z afacostag $

import traci.constants
personIDs = traci.edge.getUniversal(constants.LAST_STEP_PERSON_ID_LIST, edgeID);