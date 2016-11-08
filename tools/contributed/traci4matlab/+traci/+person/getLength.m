function length = getLength(personID)
%getLength Returns the length in m of the given person.
%   length = getLength(PERSONID) Returns the length in m of the given person.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLength.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
length = traci.person.getUniversal(constants.VAR_LENGTH, personID);