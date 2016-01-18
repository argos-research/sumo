function length = getLength(personID)
%getLength Returns the length in m of the given person.
%   length = getLength(PERSONID) Returns the length in m of the given person.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLength.m 25 2015-06-25 22:38:10Z afacostag $

import traci.constants
length = traci.person.getUniversal(constants.VAR_LENGTH, personID);